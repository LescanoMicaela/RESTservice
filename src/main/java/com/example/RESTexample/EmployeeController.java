package com.example.RESTexample;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
//indicates that the data returned by each method will be written straight
// into the response body instead of rendering a template.
public class EmployeeController {

    //Injecting dependencies by constructor.
    private final EmployeeRepository repository;
    private final EmployeeResourceAssembler assembler;

    EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    //Root
//    @GetMapping("/employees")
// this path can be only test on POSTMAN
    //added aplication/json so can be tested on chrome with no exceptions, not just POSTMAN
    @GetMapping(value="/employees", produces = "application/json; charset=UTF-8")
    Resources<Resource<Employee>> findAll(){
        //Resources encapsulate collections of employee resources
        List<Resource<Employee>> employees = repository.findAll().stream()
//                .map(employee -> getResource(employee))
                //Refactored with a private method
//                        new Resource<>(employee,
//                        linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees")))

                //ussign assembler to create resource
                .map(assembler::toResource)
                //method references
                .collect(Collectors.toList());
        return new Resources<>(employees,
                linkTo(methodOn(EmployeeController.class).findAll()).withSelfRel());
    }

    //new to refacotr to not save in controller
//    @PostMapping("employees")
    @PostMapping(value="/employees", produces = "application/json; charset=UTF-8")

    ResponseEntity<?> register(@RequestBody Employee newEmployee) throws URISyntaxException {
        //The new Employee object is saved,
        // But the resulting object is wrapped using the EmployeeResourceAssembler.
        Resource<Employee> resource = assembler.toResource(repository.save(newEmployee));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping(value="/employees/{id}", produces = "application/json; charset=UTF-8")
    Resource<Employee> findOne(@PathVariable Long id){
        Employee employee = repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
//        return new Resource<>(employee,
//                // asks that Spring HATEOAS build a link to the EmployeeController's
//                // findOne() method, and flag it as a self link.
//                linkTo(methodOn(EmployeeController.class).findOne(id)).withSelfRel(),
//                // asks Spring HATEOAS to build a link to the aggregate root,
//                // findAll(), and call it "employees".
//                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
//        return getResource(employee);

        //ussing assembler to return the resource
        return assembler.toResource(employee);

    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replacerEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) throws URISyntaxException{
        Employee updatedEmployee = repository.findById(id)
                .map(employee ->{
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                }).orElseGet(()-> {
                    newEmployee.setId(id);
                    return  repository.save(newEmployee);
                });
        Resource<Employee> resource = assembler.toResource(updatedEmployee);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id){
     return repository.findById(id)
             .map(employee -> {repository.deleteById(id);
             return ResponseEntity.noContent().build();
             })
             .orElseThrow(()-> new EmployeeNotFoundException(id));
    }

//    private Resource<Employee> getResource(Employee employee){
//        return new Resource<>(employee,
//                // asks that Spring HATEOAS build a link to the EmployeeController's
//                // findOne() method, and flag it as a self link.
//                linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
//                // asks Spring HATEOAS to build a link to the aggregate root,
//                // findAll(), and call it "employees".
//                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
//    }
}
