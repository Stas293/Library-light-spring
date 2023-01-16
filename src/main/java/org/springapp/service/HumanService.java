package org.springapp.service;

import org.springapp.dao.BookDAO;
import org.springapp.dao.HumanDAO;
import org.springapp.models.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {
    private final HumanDAO humanDAO;
    private final BookDAO bookDAO;

    @Autowired
    public HumanService(HumanDAO humanDAO, BookDAO bookDAO) {
        this.humanDAO = humanDAO;
        this.bookDAO = bookDAO;
    }

    public void save(Human human) {
        humanDAO.save(human);
    }

    public List<Human> getPeople() {
        return humanDAO.getList();
    }

    public Human getPerson(int id) {
        return humanDAO.get(id).map(human -> {
            human.setBooks(bookDAO.getBooksByHumanId(human.getId()));
            return human;
        }).orElse(null);
    }

    public void update(Human person) {
        humanDAO.update(person);
    }

    public void delete(int id) {
        humanDAO.delete(id);
    }

    public Human getByFullName(Human human) {
        return humanDAO.getByFullName(human).orElse(null);
    }
}
