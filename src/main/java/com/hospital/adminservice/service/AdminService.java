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

    @Value("${doctor.service.url}")
    private String docUrl;
    @Value("${patient.service.url}")
    private String patUrl;
    @Value("${appointment.service.url}")
    private String apptUrl;

    public DashboardStatsDTO getDashboardStats() {
        List<AdminDoctorDTO> doctors = getAllDoctors();
        List<AdminPatientDTO> patients = getAllPatients();
        List<AdminAppointmentDTO> appointments = getAllAppointments();
        List<AdminSlotDTO> allSlots = getAllSlots();

        long pendingAppointments = appointments.stream().filter(a -> "PENDING".equalsIgnoreCase(a.getStatus())).count();
        long confirmedAppointments = appointments.stream().filter(a -> "CONFIRMED".equalsIgnoreCase(a.getStatus())).count();
        long availableSlots = allSlots.stream().filter(AdminSlotDTO::getAvailable).count();

        DashboardStatsDTO stats = new DashboardStatsDTO();
        stats.setTotalDoctors(doctors.size());
        stats.setTotalPatients(patients.size());
        stats.setTotalAppointments(appointments.size());
        stats.setPendingAppointments(pendingAppointments);
        stats.setConfirmedAppointments(confirmedAppointments);
        stats.setAvailableSlots(availableSlots);
        return stats;
    }

    public List<AdminDoctorDTO> getAllDoctors() {
        AdminDoctorDTO[] arr = rest.getForObject(docUrl + "/api/doctors", AdminDoctorDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminDoctorDTO[0]);
    }

    public void deleteDoctor(Long id) {
        rest.delete(docUrl + "/api/doctors/{id}", id);
    }

    public List<AdminPatientDTO> getAllPatients() {
        AdminPatientDTO[] arr = rest.getForObject(patUrl + "/api/patients", AdminPatientDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminPatientDTO[0]);
    }

    public void deletePatient(Long id) {
        rest.delete(patUrl + "/api/patients/{id}", id);
    }

    public List<AdminSlotDTO> getAllSlots() {
        return getAllDoctors().stream()
                .map(doctor -> getSlotsForDoctor(doctor.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<AdminSlotDTO> getSlotsForDoctor(Long doctorId) {
        String url = docUrl + "/api/doctors/" + doctorId + "/slots";
        AdminSlotDTO[] arr = rest.getForObject(url, AdminSlotDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminSlotDTO[0]);
    }

    public void deleteSlot(Long doctorId, Long slotId) {
        rest.delete(docUrl + "/api/doctors/{d}/slots/{s}", doctorId, slotId);
    }

    public List<AdminAppointmentDTO> getAllAppointments() {
        AdminAppointmentDTO[] arr = rest.getForObject(apptUrl + "/api/appointments", AdminAppointmentDTO[].class);
        return Arrays.asList(arr != null ? arr : new AdminAppointmentDTO[0]);
    }

    public void deleteAppointment(Long id) {
        rest.delete(apptUrl + "/api/appointments/{id}", id);
    }
}