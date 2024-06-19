package ru.study.base.tgjavabot.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.base.tgjavabot.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/manage")
@RestController
@Transactional
public class RolesManageController {

    private final UserService userService;

    @GetMapping("/whatRoleIs")
    public ResponseEntity<String> getRole(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.getUserRole(username));
    }
    @PutMapping("/changeRole")
    public ResponseEntity<Void> changeRole(@RequestParam("username") String username, @RequestParam("role") String role) {
        userService.putUserRole(username, role);
        return ResponseEntity.ok().build();
    }
}
