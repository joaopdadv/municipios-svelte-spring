// AuthService.java
package br.com.municipios.service;

import br.com.municipios.config.JwtUtil;
import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.auth.dto.AuthRequest;
import br.com.municipios.entity.auth.dto.AuthResponse;
import br.com.municipios.entity.auth.dto.UserResponse;
import br.com.municipios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public AuthResponse login(AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(request.getEmail())
        );
        UserResponse userResp = toUserResponse(user);

        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setUser(userResp);
        return resp;
    }

    public UserResponse register(User user) throws IllegalArgumentException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    private UserResponse toUserResponse(User u) {
        UserResponse dto = new UserResponse();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole());
        return dto;
    }

    public enum UserRole {
        ALUNO(0), PROFESSOR(1), ADMIN(2);

        private final int value;

        UserRole(int value) { this.value = value; }

        public int getValue() { return value; }

        public static UserRole fromValue(int value) {
            for (UserRole role : values()) {
                if (role.getValue() == value) return role;
            }
            throw new IllegalArgumentException("Invalid role value: " + value);
        }
    }
}
