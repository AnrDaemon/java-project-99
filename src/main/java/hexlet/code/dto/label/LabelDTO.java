package hexlet.code.dto.label;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1000)
    private String name;

    private LocalDate createdAt;
}
