package hexlet.code.dto.task;

import java.time.LocalDate;
import java.util.Set;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private Long index;

    private LocalDate createdAt;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;

    private String title;
    private String content;
    private String status;

    private Set<Long> taskLabelIds;
}
