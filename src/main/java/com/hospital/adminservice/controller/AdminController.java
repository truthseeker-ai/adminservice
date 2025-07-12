package com.hospital.adminservice.controller;

import com.hospital.adminservice.dto.*;
import com.hospital.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Hard-coded admin credentials
    private static final String ADMIN_USER = "admin@gmail.com";
    private static final String ADMIN_PASS = "Admin";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest req) {
        if (ADMIN_USER.equalsIgnoreCase(req.getUsername())
                && ADMIN_PASS.equals(req.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<AdminDoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<AdminPatientDTO>> getAllPatients() {
        return ResponseEntity.ok(adminService.getAllPatients());
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        adminService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AdminAppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(adminService.getAllAppointments());
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        adminService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/slots")
    public ResponseEntity<List<AdminSlotDTO>> getAllSlots() {
        return ResponseEntity.ok(adminService.getAllSlots());
    }

    @DeleteMapping("/doctors/{doctorId}/slots/{slotId}")
    public ResponseEntity<Void> deleteSlot(
            @PathVariable Long doctorId,
            @PathVariable Long slotId) {
        // ✨ DEBUG LOG ✨
        System.out.println("ADMIN-SERVICE: Received DELETE /api/admin/doctors/"
                + doctorId + "/slots/" + slotId);

        adminService.deleteSlot(doctorId, slotId);

        System.out.println("ADMIN-SERVICE: Successfully called doctor-service to delete slot "
                + slotId);
        return ResponseEntity.noContent().build();
    }
}
