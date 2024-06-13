package ru.study.base.tgjavabot.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.study.base.tgjavabot.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController{

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> registration(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        userService.registration(username, password);
        return ResponseEntity.ok().build();
    }
}
