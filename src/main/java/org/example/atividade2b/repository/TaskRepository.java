package org.example.atividade2b.repository;

import org.example.atividade2b.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
