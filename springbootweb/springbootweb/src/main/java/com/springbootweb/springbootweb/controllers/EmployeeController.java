package com.springbootweb.springbootweb.controllers;


import com.springbootweb.springbootweb.dto.EmployeeDTO;

import com.springbootweb.springbootweb.exceptions.ResourceNotFoundException;
import com.springbootweb.springbootweb.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController {

//    @GetMapping(path = "/getSuper")
//    public String getSuper(){
//        return "here is message";
//    }

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable  Long employeeID){
  Optional<EmployeeDTO>  employeeDTO = employeeService.getEmployeeById(employeeID);
  return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
          .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id" + employeeID));
  }


@GetMapping
    public ResponseEntity< List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false,name ="inputAge") Integer  age,
                                               @RequestParam(required = false) String name){
      return ResponseEntity.ok(employeeService.getAllEmployee());
  }

  @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){

      EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
      return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{employeeID}")
 public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable  Long employeeID)
  {
    return  ResponseEntity.ok(employeeService.updateEmployeeById(employeeID,employeeDTO));

  }
@DeleteMapping(path = "/{employeeID}")
public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable  Long employeeID)
{
    boolean gotDeleted =employeeService.deleteEmployeeById(employeeID);
    if(gotDeleted) return  ResponseEntity.ok(true);
   return ResponseEntity.notFound().build();

}
@PatchMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String , Object> updates,
                                                  @PathVariable Long employeeID){
    EmployeeDTO employeeDTO =  employeeService.updatePartialEmployeeById(employeeID,updates);
    if(employeeDTO == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(employeeDTO);
}


}
