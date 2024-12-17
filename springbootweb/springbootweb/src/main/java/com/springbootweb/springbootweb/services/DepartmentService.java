package com.springbootweb.springbootweb.services;


import com.springbootweb.springbootweb.dto.DepartmentDTO;
import com.springbootweb.springbootweb.dto.EmployeeDTO;
import com.springbootweb.springbootweb.entities.DepartmentEntity;
import com.springbootweb.springbootweb.entities.EmployeeEntity;
import com.springbootweb.springbootweb.exceptions.ResourceNotFoundException;
import com.springbootweb.springbootweb.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
     private final DepartmentRepository departmentRepository;
     private final ModelMapper modelMapper;


    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<DepartmentDTO> getDepartmentById(@PathVariable Long departmentID){

      return departmentRepository.findById(departmentID).map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class));

    }

    public List <DepartmentDTO> getAllDepartments(){
        List<DepartmentEntity> departmentEntities= departmentRepository.findAll();
        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class))
                .collect(Collectors.toList());

    }

    public  DepartmentDTO createDepartment(DepartmentDTO inputDepartment){

        DepartmentEntity toSave = modelMapper.map(inputDepartment,DepartmentEntity.class);
        DepartmentEntity saveDepartmentEntity = departmentRepository.save(toSave);
        return modelMapper.map(saveDepartmentEntity,DepartmentDTO.class);

    }

    public void isExitsByDepartmentID(Long departmentID){
        boolean exits = departmentRepository.existsById(departmentID);
        if(!exits) throw  new ResourceNotFoundException("department not found with id:" + departmentID);

    }

    public DepartmentDTO updateDepartmentBYID(Long departmentID, DepartmentDTO departmentDTO){
        isExitsByDepartmentID(departmentID);

        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO,DepartmentEntity.class);
        departmentEntity.setId(departmentID);
        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        return modelMapper.map(savedDepartmentEntity,DepartmentDTO.class);

    }

    public boolean deleteDepartmentByID(Long departmentID){
        isExitsByDepartmentID(departmentID);
         departmentRepository.deleteById(departmentID);
        return true;
    }

}
