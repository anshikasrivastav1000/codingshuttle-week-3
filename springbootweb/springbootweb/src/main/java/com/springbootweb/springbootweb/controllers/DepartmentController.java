package com.springbootweb.springbootweb.controllers;

import com.springbootweb.springbootweb.dto.DepartmentDTO;

import com.springbootweb.springbootweb.entities.DepartmentEntity;
import com.springbootweb.springbootweb.exceptions.ResourceNotFoundException;
import com.springbootweb.springbootweb.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "department")
public class DepartmentController {
 private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{departmentID}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentID){
        Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentById(departmentID);
        return departmentDTO.map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id" + departmentID));

    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
       return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO inputDepartment){
        DepartmentDTO savedDepartment = departmentService.createDepartment(inputDepartment);
        return new  ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PutMapping(path ="/{departmentID}")
    public  ResponseEntity<DepartmentDTO> updateDepartmentBYId(@RequestBody DepartmentDTO departmentDTO,@PathVariable Long departmentID){
        return ResponseEntity.ok(departmentService.updateDepartmentBYID(departmentID,departmentDTO));
    }

    @DeleteMapping(path = "/{departmentID}")
    public ResponseEntity<Boolean> deleteDepartmentByID(@PathVariable  Long departmentID)
    {
        boolean gotDeleted =departmentService.deleteDepartmentByID(departmentID);
        if(gotDeleted) return  ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();

    }
}
