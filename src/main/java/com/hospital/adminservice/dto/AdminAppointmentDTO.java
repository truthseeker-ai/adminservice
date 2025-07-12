package com.hospital.adminservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdminAppointmentDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private String appointmentSlot;
    private String status;
}
