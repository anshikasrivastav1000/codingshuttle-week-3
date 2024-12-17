package com.springbootweb.springbootweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class DepartmentDTO {


    private long id;
    private String title;
    private boolean isActive;
    private LocalDate createdAt;

}
