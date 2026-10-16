package com.prashant.agriconnect.service;

import com.prashant.agriconnect.dto.*;
import com.prashant.agriconnect.entity.User;
import com.prashant.agriconnect.entity.UserRole;
import com.prashant.agriconnect.exception.BadRequestException;
import com.prashant.agriconnect.exception.ResourceNotFoundException;
import com.prashant.agriconnect.repository.UserRepository;
import com.prashant.agriconnect.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService) {
        this.users = users; this.encoder = encoder; this.jwtService = jwtService;
    }

    public AuthResponse signup(SignupRequest request) {
        if (users.existsByEmailIgnoreCase(request.email())) throw new BadRequestException("User Already Exist");
        UserRole role = parseRole(request.accountType());
        if (role == UserRole.FARMER && (request.pmkisanId() == null || request.pmkisanId().isBlank()))
            throw new BadRequestException("PMKISAN ID is required for farmer accounts");
        if (role == UserRole.CUSTOMER && request.pmkisanId() != null && !request.pmkisanId().isBlank())
            throw new BadRequestException("Customer should not have PMKISAN ID");

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPassword(encoder.encode(request.password()));
        user.setRole(role);
        user.setPmKisanId(blankToNull(request.pmkisanId()));
        user.setPhoneNumber(request.phoneNumber().trim());
        user.setAddress(request.address().trim());
        users.save(user);
        return new AuthResponse("User created successfully", null, toResponse(user));
    }

    public AuthResponse login(LoginRequest request) {
        User user = users.findByEmailIgnoreCase(request.email()).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        UserRole requested = parseRole(request.accountType());
        if (user.getRole() != requested) throw new BadRequestException("Account type does not match this account");
        if (!encoder.matches(request.password(), user.getPassword())) throw new BadRequestException("Invalid email or password");
        if (user.getRole() == UserRole.FARMER && !safeEquals(request.pmkisanId(), user.getPmKisanId()))
            throw new BadRequestException("PMKISAN ID is not verified");
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse("Login successful", token, toResponse(user));
    }

    public UserResponse getUser(String id) {
        return toResponse(users.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    private UserRole parseRole(String role) {
        try { return UserRole.valueOf(role.toUpperCase()); }
        catch (Exception e) { throw new BadRequestException("Invalid account type"); }
    }
    private boolean safeEquals(String a, String b) { return a != null && b != null && a.equals(b); }
    private String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    public static UserResponse toResponse(User u) { return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole().name(), u.getPmKisanId(), u.getPhoneNumber(), u.getAddress()); }
}
