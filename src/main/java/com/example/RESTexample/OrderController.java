package com.example.RESTexample;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class OrderController {

    private OrderRepository orderRepository;
    private final OrderResourceAssembler assembler;

    OrderController(OrderRepository orderRepository,OrderResourceAssembler assembler){
        this.orderRepository = orderRepository;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    Resources<Resource<Order>> findAll(){
        List<Resource<Order>> orders = orderRepository.findAll()
                .stream().map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(orders,
                linkTo(methodOn(OrderController.class).findAll()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    Resource<Order> findOne(@PathVariable Long id){
        return assembler.toResource(
                orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException(id)));
    }

    @PostMapping("/orders")
    ResponseEntity<Resource<Order>> newOrder(@RequestBody Order order){
        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).findOne(newOrder.getId())).toUri())
                .body(assembler.toResource(newOrder));

    }



    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id){
        return orderRepository.findById(id).map(o ->  {if (o.getStatus() == Status.IN_PROGRESS){
            o.setStatus(Status.CANCELLED);
            return  ResponseEntity.ok(assembler.toResource(orderRepository.save(o)));
        }
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + o.getStatus() + " status"));}
        )
        .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<ResourceSupport> complete (@PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
        if (order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource((orderRepository.save(order))));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel an order that is in the " + order.getStatus() + " status"));

    }
}
