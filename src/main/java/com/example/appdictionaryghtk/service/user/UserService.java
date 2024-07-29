package com.example.appdictionaryghtk.service.user;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.appdictionaryghtk.component.JwtTokenUtils;
import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.*;
import com.example.appdictionaryghtk.dtos.response.user.LoginResponse;
import com.example.appdictionaryghtk.entity.Role;
import com.example.appdictionaryghtk.entity.Token;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.exceptions.ExpiredTokenException;
import com.example.appdictionaryghtk.repository.RoleRepository;
import com.example.appdictionaryghtk.repository.TokenRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.service.email.IConfirmEmailService;
import com.example.appdictionaryghtk.service.token.ITokenService;
import com.example.appdictionaryghtk.util.Gender;
import com.example.appdictionaryghtk.util.UserStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
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
    private final ITokenService tokenService;
    private final TokenRepository tokenRepository;
    private final Cloudinary cloudinary;
    @Override
    public User createUser(CreateUserRequest userDTO) {
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
    public LoginResponse login(LoginRequest loginRequest, String userAgent) {
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

        String token = jwtTokenUtils.generateToken(user);
        User userDetail = getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

        return LoginResponse.builder()
                .token(jwtToken.getToken())
                .tokenType(jwtToken.getTokenType())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        return userAgent.toLowerCase().contains("mobile");
    }

    @Override
    public User getUserDetailsFromToken(String token) {
        if(jwtTokenUtils.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String username = jwtTokenUtils.extractUsername(token);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
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
    public UserDTO getMyInfo() throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not existed"));
        return UserDTO.toUser(user);
    }

    @Override
    public void logout(String token) {
        String cleanToken = token.replace("Bearer ", "");
        Token userToken = tokenRepository.findByToken(cleanToken)
                .orElseThrow(() -> new DataNotFoundException("Token not found"));
        tokenRepository.delete(userToken);
    }

    @Override
    public void changePassword(String token, ChangePasswordRequest changePasswordRequest) {
        String existingToken = token.replace("Bearer ", "");
        if(jwtTokenUtils.isTokenExpired(existingToken)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String username = jwtTokenUtils.extractUsername(existingToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the current password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUserInfo(Integer userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (updateUserRequest.getFullname() != null) {
            user.setFullname(updateUserRequest.getFullname());
        }
        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getDateOfBirth() != null) {
            user.setDateOfBirth(updateUserRequest.getDateOfBirth());
        }
        if (updateUserRequest.getGender() != null) {
            user.setGender(Gender.valueOf(updateUserRequest.getGender().toUpperCase()));
        }

        userRepository.save(user);

        return UserDTO.toUser(user);
    }

    @Override
    public UserDTO updateAvatar(Integer userId, MultipartFile avatarFile) throws IOException {
        if (avatarFile == null || avatarFile.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }
        String contentType = avatarFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File is not an image");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Map uploadResult = cloudinary.uploader().upload(avatarFile.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        String avatarUrl = (String) uploadResult.get("url");
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return UserDTO.toUser(user);
    }

    private String generateRandomString() {
        return RandomStringUtils.randomAlphabetic(6);
    }
}
