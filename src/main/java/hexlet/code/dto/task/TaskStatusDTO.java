package hexlet.code.dto.task;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusDTO {

    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String slug;

    private LocalDate createdAt;
}
