package hexlet.code.dto.user;

import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    @Email
    @NotNull
    private String email;

    private JsonNullable<String> firstName;

    private JsonNullable<String> lastName;

    @NotNull
    @Size(min = 3)
    private String password;
}
