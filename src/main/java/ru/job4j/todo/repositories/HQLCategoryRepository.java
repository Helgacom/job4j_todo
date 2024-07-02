package ru.job4j.todo.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class HQLCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }

    @Override
    public Collection<Category> getAllById(Collection<Integer> categoriesId) {
        return crudRepository.query("FROM Category c WHERE c.id IN :fCategoryList",
                Category.class, Map.of("fCategoryList", categoriesId));
    }
}
