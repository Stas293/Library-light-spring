package org.springapp.util;

import org.springapp.models.Human;
import org.springapp.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HumanValidator implements Validator {
    private final HumanService humanService;

    @Autowired
    public HumanValidator(HumanService humanService) {
        this.humanService = humanService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Human.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Human human = (Human) target;

        Human personFromDB = humanService.getByFullName(human);

        if (personFromDB != null && personFromDB.getId() != human.getId()) {
            errors.rejectValue("firstName", "", "This person already exists");
            errors.rejectValue("lastName", "", "This person already exists");
        }
    }
}
