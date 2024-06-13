package ru.study.base.tgjavabot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.study.base.tgjavabot.exception.UserAlreadyExist;
import ru.study.base.tgjavabot.model.User;
import ru.study.base.tgjavabot.model.UserAuthority;
import ru.study.base.tgjavabot.model.UserRole;
import ru.study.base.tgjavabot.repository.UserRepository;
import ru.study.base.tgjavabot.repository.UserRolesRepository;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void registration(String username, String password) {
        if(userRepository.findByUsername(username).isEmpty()) {
            User user = userRepository.save(
                    new User()
                            .setId(null)
                            .setUsername(username)
                            .setPassword(passwordEncoder.encode(password))
                            .setLocked(false)
                            .setExpired(false)
                            .setEnabled(true)
            );
            userRolesRepository.save(new UserRole(null, UserAuthority.PLACE_JOKES, user));
        }
        else{
            throw new UserAlreadyExist();
        }
    }

    @Override
    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Optional<UserRole> userRole = userRolesRepository.findByUser(user);
        return userRole.map(role -> role.getUserAuthority().toString())
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }


    @Override
    public void putUserRole(String username, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserRole userRole = userRolesRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userRole.setUserAuthority(UserAuthority.valueOf(role));
        userRolesRepository.save(userRole);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
