package hexlet.code.dto.task;

import org.openapitools.jackson.nullable.JsonNullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TaskStatusUpdateDTO {

    private JsonNullable<String> name;
    private JsonNullable<String> slug;
}
