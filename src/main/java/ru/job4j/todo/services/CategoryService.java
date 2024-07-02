package ru.job4j.todo.services;

import ru.job4j.todo.model.Category;

import java.util.Collection;

public interface CategoryService {

    Collection<Category> findAll();

    Collection<Category> getAllById(Collection<Integer> categoriesId);
}
