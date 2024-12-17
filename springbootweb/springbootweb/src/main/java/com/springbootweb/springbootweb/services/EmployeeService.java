package com.springbootweb.springbootweb.services;


import com.springbootweb.springbootweb.dto.EmployeeDTO;
import com.springbootweb.springbootweb.entities.EmployeeEntity;
import com.springbootweb.springbootweb.exceptions.ResourceNotFoundException;
import com.springbootweb.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> getEmployeeById(Long employeeID) {
//      Optional<EmployeeEntity>   employeeEntity = employeeRepository.findById(employeeID);
//
// return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));

        return  employeeRepository.findById(employeeID).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));


    }

    public List<EmployeeDTO> getAllEmployee() {
      List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
     return   employeeEntities
               .stream()
               .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
        .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
        return  modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeID, EmployeeDTO employeeDTO) {
       isExistsByEmployeeID(employeeID);

       EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
       employeeEntity.setId(employeeID);
       EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
       return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);

    }

    public void isExistsByEmployeeID(Long employeeID){
        boolean exists = employeeRepository.existsById(employeeID);
        if(!exists) throw new ResourceNotFoundException("employee not found with id:" + employeeID);

    }

    public Boolean deleteEmployeeById(Long employeeID) {
        isExistsByEmployeeID(employeeID);

        employeeRepository.deleteById(employeeID);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeID, Map<String, Object> updates) {
       isExistsByEmployeeID(employeeID);

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).get();
        updates.forEach((field,value) ->{
        Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
        fieldToBeUpdated.setAccessible(true);
        ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        
        });
return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
