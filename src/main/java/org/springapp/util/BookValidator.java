package org.springapp.util;

import org.springapp.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getPublished().after(new Date())) {
            errors.rejectValue("published", "", "Published date cannot be in the future");
        }

        if (book.getPublished().before(new Date(0))) {
            errors.rejectValue("published", "", "Published date cannot be before 1970");
        }
    }
}
