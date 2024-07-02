package ru.job4j.todo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.services.CategoryService;
import ru.job4j.todo.services.PriorityService;
import ru.job4j.todo.services.TaskService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача не найдена");
            return "error/error";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/detailed";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/new";
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findDone());
        return "tasks/done";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam List<Integer> categoryList) {
        task.setUser(user);
        task.setCategories(new HashSet<>(categoryService.getAllById(categoryList)));
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String getUpdate(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("error", "Задача не найдена");
            return "error/error";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @RequestParam List<Integer> categoryList) {
        task.getCategories().addAll(categoryService.getAllById(categoryList));
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/updateDone/{id}")
    public String updateDone(Model model, @ModelAttribute Task task) {
        var isUpdated = taskService.updateState(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание не найдено");
            return "error/error";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание указанным идентификатором не найдена");
            return "error/error";
        }
        return "redirect:/tasks";
    }
}
