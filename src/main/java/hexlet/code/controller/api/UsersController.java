package hexlet.code.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/users")
@AllArgsConstructor
public class UsersController {

    private UserService userService;

    /**
     * @return Users list
     */
    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> index() {
        var result = userService.index();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    /**
     * GET /api/users/{id}.
     *
     * @param id
     * @return User
     */
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable Long id) {
        return userService.show(id);
    }

    /**
     * POST /api/users.
     *
     * @param dto
     * @return User.
     */
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid UserCreateDTO dto) {
        return userService.create(dto);
    }

    /**
     * PUT /api/users/{id}.
     *
     * @param id
     * @param dto
     * @return User.
     */
    @RequestMapping(path = "/{id}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    @PreAuthorize("@userUtils.isAuthor(#id)")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
        return userService.update(id, dto);
    }

    /**
     * DELETE /api/users/{id}.
     *
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("@userUtils.isAuthor(#id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
