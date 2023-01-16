package org.springapp.controllers;

import jakarta.validation.Valid;
import org.springapp.constants.HumanConstants;
import org.springapp.models.Human;
import org.springapp.service.HumanService;
import org.springapp.util.HumanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/people")
public class HumanController {
    private final HumanService humanService;
    private final HumanValidator humanValidator;

    @Autowired
    public HumanController(HumanService humanService, HumanValidator humanValidator) {
        this.humanService = humanService;
        this.humanValidator = humanValidator;
    }

    @GetMapping()
    public String indexPage(Model model) {
        model.addAttribute("people", humanService.getPeople());
        return HumanConstants.PEOPLE_INDEX;
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Human person, BindingResult bindingResult) {
        humanValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return HumanConstants.PEOPLE_NEW;
        }
        humanService.save(person);
        return HumanConstants.REDIRECT_PEOPLE;
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Human person) {
        return HumanConstants.PEOPLE_NEW;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", humanService.getPerson(id));
        return HumanConstants.PEOPLE_PERSON;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", humanService.getPerson(id));
        return HumanConstants.PEOPLE_EDIT;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Human person, BindingResult bindingResult) {
        humanValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return HumanConstants.PEOPLE_EDIT;
        }

        humanService.update(person);
        return HumanConstants.REDIRECT_PEOPLE_ID + person.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        humanService.delete(id);
        return HumanConstants.REDIRECT_PEOPLE;
    }
}
