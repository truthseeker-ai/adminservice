package com.hospital.adminservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdminPatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
}
