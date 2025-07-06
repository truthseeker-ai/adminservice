package com.hospital.adminservice.dto;
import lombok.Data;

@Data
public class DashboardStatsDTO {
    private long totalDoctors;
    private long totalPatients;
    private long totalAppointments;
    private long pendingAppointments;
    private long confirmedAppointments;
    private long availableSlots;
}
