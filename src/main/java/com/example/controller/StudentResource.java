package com.example.controller;


import com.example.entity.Student;
import com.example.repo.StudentRepository;
import com.example.service.MyService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.List;

@Path("/student")
@Transactional
public class StudentResource {

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private MyService myService;

    @POST
    @Consumes("application/json")
    public Response createProduct(Student product) {
        product = studentRepository.save(product);
        return Response.ok(product).build();
    }

    @GET
    @Produces("application/json")
    public List<Student> getAllProducts() {
        System.out.println("Get all sudents invoked");
        // Create a Uni that emits an item after a delay
        //Uni.createFrom().item("Hello, Mutiny!")
//        Uni.createFrom().item(() -> {
//                    // Simulate a long-running operation
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                    return "Task completed successfully!";
//                })
//                //.onItem().delayIt().by(Duration.ofMillis(5000)) // Introduce a small delay
//                .onItem().transform(String::toUpperCase) // Transform the item to uppercase
//                .subscribe().with(
//                        item -> System.out.println("Received item: " + item), // Callback for successful item emission
//                        failure -> System.err.println("Failed with: " + failure.getMessage()) // Callback for failure
//                );
//        myService.performAsyncTask()
//                .onItem().transform(result -> {
//                    // Action on successful completion
//                    System.out.println("Received result: " + result);
//                    return "Task triggered and subscribed successfully!";
//                })
//                .onFailure().transform(failure -> {
//                    // Action on failure
//                    System.err.println("Task failed: " + failure.getMessage());
//                    return new Throwable("Task failed!");
//                });
//
        myService.performAsyncTask()
                .subscribe().with(
                        result -> {
                            System.out.println("Received result: " + result);
                        },
                        failure -> {
                            System.err.println("Task failed: " + failure.getMessage());
                        }
                );

        System.out.println("Get all students fetched");
        Student oj = new Student();
        oj.setName("Test");
        return List.of(oj);
        //return studentRepository.findAll();

    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Student getProduct(@PathParam("id") String id) {
        return studentRepository.findById(id).get();
    }

    @PUT
    @Consumes("application/json")
    public Response updateProduct(Student product) {
        studentRepository.save(product);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") String id) {
        studentRepository.deleteById(id);
        return Response.ok().build();
    }

}
