package ru.job4j.todo.services;

import ru.job4j.todo.model.Priority;

import java.util.Collection;

public interface PriorityService {

    Collection<Priority> findAll();
}
