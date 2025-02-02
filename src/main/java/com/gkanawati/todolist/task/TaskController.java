package com.gkanawati.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkanawati.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("")
  public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = (UUID) request.getAttribute("userId");
    taskModel.setUserId(userId);

    var currentDate = LocalDateTime.now();
    if (currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("{ \"message\": \"A data de término não pode ser anterior a data atual\" }");
    }

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("{ \"message\": \"A data de início não pode ser posterior a data de término\" }");
    }

    var createdTask = this.taskRepository.save(taskModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @GetMapping("")
  public ResponseEntity<Object> getAll(HttpServletRequest request) {
    var userId = (UUID) request.getAttribute("userId");

    var tasks = this.taskRepository.findByUserId(userId);
    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, HttpServletRequest request,
      @PathVariable UUID taskId) {
    var userId = (UUID) request.getAttribute("userId");

    var queryTask = this.taskRepository.findById(taskId);

    if (queryTask.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Task not found\" }");
    }

    var task = queryTask.get();

    if (task == null || !task.getUserId().equals(userId)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{ \"message\": \"Task not found\" }");
    }

    Utils.copyNonNullProperties(taskModel, task);

    this.taskRepository.save(task);
    return ResponseEntity.status(HttpStatus.OK).body(task);
  }
}
