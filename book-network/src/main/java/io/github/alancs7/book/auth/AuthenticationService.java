package io.github.alancs7.book.auth;

import io.github.alancs7.book.exception.RoleNotFoundException;
import io.github.alancs7.book.role.RoleRepository;
import io.github.alancs7.book.user.Token;
import io.github.alancs7.book.user.TokenRepository;
import io.github.alancs7.book.user.User;
import io.github.alancs7.book.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("User role not found"));

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) {
        var newToken = generateAndSaveActivationToken(user);

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int lenght) {
        String characters = "0123456789";

        var codeBuilder = new StringBuilder();
        var secureRandom = new SecureRandom();

        for (int i = 0; i < lenght; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
