package ru.job4j.todo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repositories.HQLPriorityRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private final HQLPriorityRepository priorityRepository;

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
