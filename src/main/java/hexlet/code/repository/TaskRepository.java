package hexlet.code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    Optional<Task> findByName(String name);

    boolean existsByAssigneeId(Long assigneeId);

    boolean existsByTaskStatus(@NotNull TaskStatus taskStatus);

    // boolean existsByLabelsContaining(Label label);
}
