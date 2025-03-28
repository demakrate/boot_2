package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                               RedirectAttributes redirectAttributes) {
        try {
            registrationService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Регистрация прошла успешно");
            return "redirect:/";
        } catch (RuntimeException e) {
            System.out.println(e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Пользователь с такой почтой уже существует");
            return "redirect:/";
        }
    }

    @GetMapping("/error")
    public String showLoginForm(Model model) {
        model.addAttribute("errorMessage", "Неверное имя пользователя или пароль.");
        return "index";
    }
}
