package com.example.RESTexample;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {

    @Override
    public Resource<Employee> toResource(Employee employee) {
        // this method convers a non-resource object (Employee) into a resource-based object (Resource<Employee>).

        return new Resource<>(employee,
                linkTo(methodOn(EmployeeController.class).findOne(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).findAll()).withRel("employees"));
    }
}
