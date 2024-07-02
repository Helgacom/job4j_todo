package ru.job4j.todo.repositories;

import ru.job4j.todo.model.Category;

import java.util.Collection;

public interface CategoryRepository {

    Collection<Category> findAll();

    Collection<Category> getAllById(Collection<Integer> categoriesId);
}
