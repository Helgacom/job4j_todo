package ru.job4j.todo.repositories;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> create(Task task);

    Collection<Task> findAll();

    Optional<Task> findById(int id);

    boolean update(Task tasks);

    boolean updateState(Task tasks);

    boolean deleteById(int id);

    Collection<Task> findNew();

    Collection<Task> findDone();
}
