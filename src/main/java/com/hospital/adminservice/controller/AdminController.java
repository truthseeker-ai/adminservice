package com.hospital.adminservice.controller;

import com.hospital.adminservice.dto.*;
import com.hospital.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService admin;

    @GetMapping("/doctors")
    public ResponseEntity<List<AdminDoctorDTO>> doctors() {
        return ResponseEntity.ok(admin.getAllDoctors());
    }

    @GetMapping("/patients")
    public ResponseEntity<List<AdminPatientDTO>> patients() {
        return ResponseEntity.ok(admin.getAllPatients());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AdminAppointmentDTO>> appointments() {
        return ResponseEntity.ok(admin.getAllAppointments());
    }

    @GetMapping("/appointments/status/{status}")
    public ResponseEntity<List<AdminAppointmentDTO>> appointmentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(admin.getAppointmentsByStatus(status));
    }

    @GetMapping("/slots")
    public ResponseEntity<List<AdminSlotDTO>> slots() {
        return ResponseEntity.ok(admin.getAllSlots());
    }

    @GetMapping("/slots/doctor/{docId}")
    public ResponseEntity<List<AdminSlotDTO>> slotsByDoctor(@PathVariable Long docId) {
        return ResponseEntity.ok(admin.getSlotsByDoctor(docId));
    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<DashboardStatsDTO> dashboardStats() {
        return ResponseEntity.ok(admin.getDashboardStats());
    }
}
