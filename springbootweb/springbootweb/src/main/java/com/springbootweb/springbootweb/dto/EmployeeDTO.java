package com.springbootweb.springbootweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.springbootweb.springbootweb.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {


    private Long id;

    @NotEmpty(message = "name of an employee can not be empty")
    @Size(min = 3, max = 10, message = "number of characters in name should be in range : [ 3 , 10]")
    private String name;

    @Email(message = "email should be a valid email")
     private String email;
    @Max(value = 80,message = "age cannot be greater than 80")
    @Min(value = 80,message = "age cannot be LESS than 18")
     private Integer age;
    @NotBlank(message = "role of an employee cannot be blank")
   // @Pattern(regexp = "^(ADMIN|USER) $", message = "role of employee can be user or admin")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "salary of employe should not be null")
    @Positive(message = "salary of employee should be positive")
    @Digits(integer =  6 , fraction = 2,message = "the salary cab be in form XXXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private Double salary;
    @PastOrPresent(message = "dateOfJoining field in employee cannot be in the future")

      private LocalDate dateOfJoining;

    @AssertTrue(message = "employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive;






}
