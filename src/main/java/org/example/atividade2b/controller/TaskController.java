package org.example.atividade2b.controller;

import org.example.atividade2b.model.Task;
import org.example.atividade2b.model.TaskPriority;
import org.example.atividade2b.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/grouped")
    public Map<String, List<Task>> getTasksGroupedByStatus() {
        return taskService.getTasksGroupedByStatus();
    }

    @GetMapping("/priority")
    public List<Task> getTasksByPriority(@RequestParam TaskPriority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/overdue")
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}/move")
    public Task moveTask(@PathVariable Long id) {
        return taskService.moveTask(id);
    }
}
