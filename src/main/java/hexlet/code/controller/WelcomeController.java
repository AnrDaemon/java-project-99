package hexlet.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.sentry.Sentry;

@RestController
public class WelcomeController {

    /**
     * @return Welcome string.
     */
    @GetMapping(path = "/welcome")
    public String welcome() {
        return "Welcome to Spring";
    }

    /**
     * Sentry test endpoint.
     */
    @GetMapping(path = "/test")
    public void testErrorNext() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
