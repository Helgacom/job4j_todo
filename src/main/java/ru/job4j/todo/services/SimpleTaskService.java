package ru.job4j.todo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repositories.HQLTaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final HQLTaskRepository taskRepository;

    @Override
    public Optional<Task> create(Task task) {
        return taskRepository.create(task);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean update(Task tasks) {
        return taskRepository.update(tasks);
    }

    @Override
    public boolean updateState(Task tasks) {
        return taskRepository.updateState(tasks);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Collection<Task> findDone() {
        return taskRepository.findDone();
    }

    @Override
    public Collection<Task> findNew() {
        return taskRepository.findNew();
    }
}
