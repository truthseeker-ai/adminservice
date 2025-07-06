package com.hospital.adminservice.controller;

import com.hospital.adminservice.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AuthController {

    // Hard-coded admin credentials
    private static final String ADMIN_USER = "Admin";
    private static final String ADMIN_PASS = "Admin";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest req) {
        if (ADMIN_USER.equals(req.getUsername()) && ADMIN_PASS.equals(req.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
