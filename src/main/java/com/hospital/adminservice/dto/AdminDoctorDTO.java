package com.hospital.adminservice.dto;
import lombok.Data;

@Data
public class AdminDoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private Integer yearsOfExperience;
    private int appointmentCount;
}
