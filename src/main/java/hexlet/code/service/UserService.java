package hexlet.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.exception.ResourceDeletionException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * List the users.
     *
     * GET /api/users
     *
     * @return List of users.
     */
    public List<UserDTO> index() {
        var users = userRepository.findAll();
        var ret = users.stream()
                .map((user) -> userMapper.map(user))
                .toList();

        return ret;
    }

    /**
     * Show a single user.
     *
     * GET /api/users/{id}
     *
     * @param id
     * @return User.
     */
    public UserDTO show(long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        return userMapper.map(user);
    }

    /**
     * Create a new user.
     *
     * POST /api/users
     *
     * @param dto
     * @return User.
     */
    public UserDTO create(UserCreateDTO dto) {
        var newUser = userMapper.map(dto);
        newUser.setPasswordDigest(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(newUser);

        return userMapper.map(newUser);
    }

    /**
     * Update user by ID.
     *
     * PUT /api/users/{id}
     *
     * @param id
     * @param dto
     * @return UserDTO
     */
    public UserDTO update(long id, UserUpdateDTO dto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        userMapper.update(dto, user);
        if (dto.getPassword() != null && dto.getPassword().isPresent()) {
            user.setPasswordDigest(passwordEncoder.encode(dto.getPassword().get()));
        }
        userRepository.save(user);
        return userMapper.map(user);
    }

    /**
     * Removing user by ID.
     *
     * DELETE /api/users/{id}
     *
     * @param id
     */
    public void delete(long id) {
        if (taskRepository.existsByAssigneeId(id)) {
            throw new ResourceDeletionException("Нельзя удалить пользователя, у него есть задача");
        }
        userRepository.deleteById(id);
    }

    /**
     * Returns user by email.
     *
     * @param email
     * @return User.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}
