package com.hospital.adminservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdminSlotDTO {
    private Long id;
    private Long doctorId;
    private LocalDate date;
    private String time;
    private Boolean available;
}
