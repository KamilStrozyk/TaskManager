package com.wsb.taskmanager.authentication.controller;


import com.wsb.taskmanager.authentication.dto.JwtResponse;
import com.wsb.taskmanager.authentication.dto.LoginRequest;
import com.wsb.taskmanager.authentication.dto.MessageResponse;
import com.wsb.taskmanager.authentication.dto.SignupRequest;
import com.wsb.taskmanager.authentication.model.Role;
import com.wsb.taskmanager.authentication.model.RoleBE;
import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.authentication.repository.RoleRepository;
import com.wsb.taskmanager.authentication.repository.UserRepository;
import com.wsb.taskmanager.authentication.security.jwt.JwtUtils;
import com.wsb.taskmanager.authentication.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("ERROR: Username is already taken."));
        } else if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("ERROR: Email is already in use."));
        }

        UserBE user = new UserBE(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<RoleBE> roles = new HashSet<>();
        RoleBE userRole = roleRepository.findByRole(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error ROLE_USER was not found and therefore not assigned"));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));

    }
}
