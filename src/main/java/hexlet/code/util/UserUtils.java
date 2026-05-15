package hexlet.code.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hexlet.code.repository.UserRepository;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    /**
     * Validates that the actor is the owner of the record.
     *
     * @param userId
     * @return true if author is the owner.
     */
    public boolean isAuthor(long userId) {
        var userAuthorEmail = userRepository.findById(userId).get().getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userAuthorEmail.equals(authentication.getName());
    }
}
