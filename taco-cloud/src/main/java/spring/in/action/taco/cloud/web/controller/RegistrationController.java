package spring.in.action.taco.cloud.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.in.action.taco.cloud.data.UserRepository;
import spring.in.action.taco.cloud.security.RegistrationForm;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Errors errors) {
        if (errors.hasErrors()) return "registration";
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
