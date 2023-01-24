package org.springapp.service;

import org.hibernate.Hibernate;
import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springapp.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HumanService {
    private final HumanRepository humanRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    public void save(Human human) {
        humanRepository.save(human);
    }

    public List<Human> getPeople() {
        return humanRepository.findAll();
    }

    @Transactional
    public Human getPerson(long id) {
        Optional<Human> human = humanRepository.findById(id);
        human.ifPresent(value -> Hibernate.initialize(value.getBooks()));
        human.ifPresent(value -> value.getBooks().forEach(Book::setExpired));
        return human.orElse(null);
    }

    public void update(Human person) {
        humanRepository.save(person);
    }

    public void delete(long id) {
        humanRepository.deleteById(id);
    }

    public Human getByFullName(Human human) {
        return humanRepository.findByFirstNameAndLastName(human.getFirstName(), human.getLastName()).orElse(null);
    }

    public Page<Human> getPage(int pageNumber, int booksPerPage, String sortField) {
        return humanRepository.findAll(PageRequest.of(pageNumber, booksPerPage, Sort.by(sortField)));
    }
}
