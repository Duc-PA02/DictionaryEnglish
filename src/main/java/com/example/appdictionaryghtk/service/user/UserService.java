package com.example.appdictionaryghtk.service.user;

import com.example.appdictionaryghtk.component.JwtTokenUtils;
import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.ForgotPasswordRequest;
import com.example.appdictionaryghtk.dtos.request.user.LoginRequest;
import com.example.appdictionaryghtk.entity.Role;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.exceptions.ExpiredTokenException;
import com.example.appdictionaryghtk.repository.RoleRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.service.email.IConfirmEmailService;
import com.example.appdictionaryghtk.util.UserStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final IConfirmEmailService confirmEmailService;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String username = userDTO.getUsername();
        if (userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("Username has already existed");
        }
        String email = userDTO.getEmail();
        if (userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email has already existed");
        }
        User newUser = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .status(UserStatus.ACTIVE)
                .gender(userDTO.getGender())
                .email(userDTO.getEmail())
                .fullname(userDTO.getFullname())
                .build();
        userRepository.save(newUser);
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRole("USER")
                        .orElseThrow(() -> new DataNotFoundException("Role not found"));
        roles.add(role);
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public String login(LoginRequest loginRequest) throws Exception {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new DataNotFoundException("User is locked");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(), loginRequest.getPassword(), user.getAuthorities()
        );

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(user);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String username = jwtTokenUtils.extractUsername(token);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail()).orElse(null);
        if(user == null){
            throw new DataNotFoundException("EMAIL DOES NOT EXISTS");
        }
        String newPassword = generateRandomString();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        confirmEmailService.sendConfirmEmail(forgotPasswordRequest.getEmail(), newPassword);
        return "Check your email!";
    }

    @Override
    public User getUserById(Integer id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}
