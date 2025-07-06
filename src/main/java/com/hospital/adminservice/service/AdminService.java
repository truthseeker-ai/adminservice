package com.hospital.adminservice.service;

import com.hospital.adminservice.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final RestTemplate rest;

    @Value("${doctor.service.url:http://localhost:8082}")
    private String doctorUrl;

    @Value("${patient.service.url:http://localhost:8081}")
    private String patientUrl;

    @Value("${appointment.service.url:http://localhost:8083}")
    private String appointmentUrl;

    public List<AdminDoctorDTO> getAllDoctors() {
        AdminDoctorDTO[] arr = rest.getForObject(doctorUrl + "/api/doctors", AdminDoctorDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminDoctorDTO[0]);
    }

    public List<AdminPatientDTO> getAllPatients() {
        AdminPatientDTO[] arr = rest.getForObject(patientUrl + "/api/patients", AdminPatientDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminPatientDTO[0]);
    }

    public List<AdminAppointmentDTO> getAllAppointments() {
        AdminAppointmentDTO[] arr = rest.getForObject(appointmentUrl + "/api/appointments", AdminAppointmentDTO[].class);
        return enrichAppointments(arr);
    }

    public List<AdminAppointmentDTO> getAppointmentsByStatus(String status) {
        AdminAppointmentDTO[] arr = rest.getForObject(
                appointmentUrl + "/api/appointments/status/" + status,
                AdminAppointmentDTO[].class);
        return enrichAppointments(arr);
    }

    public List<AdminSlotDTO> getAllSlots() {
        AdminSlotDTO[] arr = rest.getForObject(doctorUrl + "/api/doctors/slotsAll", AdminSlotDTO[].class);
        return enrichSlots(arr);
    }

    public List<AdminSlotDTO> getSlotsByDoctor(Long docId) {
        AdminSlotDTO[] arr = rest.getForObject(
                doctorUrl + "/api/doctors/" + docId + "/slots",
                AdminSlotDTO[].class);
        return enrichSlots(arr);
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        List<AdminDoctorDTO> docs = getAllDoctors();
        List<AdminPatientDTO> pats = getAllPatients();
        List<AdminAppointmentDTO> appts = getAllAppointments();
        List<AdminSlotDTO> slots = getAllSlots();

        stats.setTotalDoctors(docs.size());
        stats.setTotalPatients(pats.size());
        stats.setTotalAppointments(appts.size());
        stats.setPendingAppointments(appts.stream()
                .filter(a -> "PENDING".equals(a.getStatus()))
                .count());
        stats.setConfirmedAppointments(appts.stream()
                .filter(a -> "CONFIRMED".equals(a.getStatus()))
                .count());
        stats.setAvailableSlots(slots.stream()
                .filter(AdminSlotDTO::getAvailable)
                .count());
        return stats;
    }

    private List<AdminAppointmentDTO> enrichAppointments(AdminAppointmentDTO[] arr) {
        if (arr == null) return List.of();
        var docs = getAllDoctors();
        var pats = getAllPatients();
        return Arrays.stream(arr).peek(a -> {
            docs.stream()
                    .filter(d -> d.getId().equals(a.getDoctorId()))
                    .findFirst()
                    .ifPresent(d -> a.setDoctorName(d.getFirstName() + " " + d.getLastName()));
            pats.stream()
                    .filter(p -> p.getId().equals(a.getPatientId()))
                    .findFirst()
                    .ifPresent(p -> a.setPatientName(p.getFirstName() + " " + p.getLastName()));
        }).collect(Collectors.toList());
    }

    private List<AdminSlotDTO> enrichSlots(AdminSlotDTO[] arr) {
        if (arr == null) return List.of();
        var docs = getAllDoctors();
        return Arrays.stream(arr).peek(s -> {
            docs.stream()
                    .filter(d -> d.getId().equals(s.getDoctorId()))
                    .findFirst()
                    .ifPresent(d -> s.setDoctorName(d.getFirstName() + " " + d.getLastName()));
        }).collect(Collectors.toList());
    }
}
