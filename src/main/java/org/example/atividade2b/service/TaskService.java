package org.example.atividade2b.service;

import org.example.atividade2b.exception.TaskAlreadyCompletedException;
import org.example.atividade2b.exception.TaskNotFoundException;
import org.example.atividade2b.model.Task;
import org.example.atividade2b.model.TaskPriority;
import org.example.atividade2b.model.TaskStatus;
import org.example.atividade2b.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Map<String, List<Task>> getTasksGroupedByStatus() {
        return taskRepository.findAll().stream()
                .collect(Collectors.groupingBy(task -> task.getStatus().name()));
    }

    public List<Task> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getPriority() == priority)
                .toList();
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now()) && task.getStatus() != TaskStatus.DONE)
                .toList();
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }

    public Task moveTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        switch (task.getStatus()) {
            case TO_DO:
                task.setStatus(TaskStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                task.setStatus(TaskStatus.DONE);
                break;
            case DONE:
                throw new TaskAlreadyCompletedException("Task is already completed");
        }
        return taskRepository.save(task);
    }
}
