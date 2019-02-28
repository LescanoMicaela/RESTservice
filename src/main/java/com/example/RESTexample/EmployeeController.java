package com.example.RESTexample;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//indicates that the data returned by each method will be written straight
// into the response body instead of rendering a template.
public class EmployeeController {

    //Injecting dependencies by constructor.
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }

    //Root
    @GetMapping("/employees")
    List<Employee> all(){
        return repository.findAll();
    }

    //new to refacotr to not save in controller
    @PostMapping("employees")
    Employee register(@RequestBody Employee newEmployee){
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    Employee findEmployee(@PathVariable Long id){

        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replacerEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        return repository.findById(id)
                .map(employee ->{
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(()-> {
                    newEmployee.setId(id);
                    return  repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employess/{id}")
    void deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
    }
}
