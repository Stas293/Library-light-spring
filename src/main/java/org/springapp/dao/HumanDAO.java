package org.springapp.dao;

import org.springapp.models.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HumanDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HumanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Human> getList() {
        return jdbcTemplate.query("SELECT * FROM human order by id", new BeanPropertyRowMapper<>(Human.class));
    }

    public void save(Human human) {
        jdbcTemplate.update("INSERT INTO human (first_name, last_name, birthdate) VALUES (?, ?, ?)",
                human.getFirstName(), human.getLastName(), human.getBirthdate());
    }

    public void update(Human human) {
        jdbcTemplate.update("UPDATE human SET first_name = ?, last_name = ?, birthdate = ? WHERE id = ?",
                human.getFirstName(), human.getLastName(), human.getBirthdate(), human.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM human WHERE id = ?", id);
    }

    public Optional<Human> get(int id) {
        return jdbcTemplate.query("SELECT * FROM human WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Human.class))
                .stream()
                .findAny();
    }

    public Optional<Human> getByFullName(Human human) {
        return jdbcTemplate.query("SELECT * FROM human WHERE first_name = ? AND last_name = ?",
                new Object[]{human.getFirstName(), human.getLastName()}, new BeanPropertyRowMapper<>(Human.class))
                .stream()
                .findAny();
    }
}
