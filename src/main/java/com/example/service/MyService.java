package com.example.service;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyService {

//    public Uni<String> performAsyncTask() {
//        return Uni.createFrom().item(() -> {
//            // Simulate a long-running operation
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            return "Task completed successfully!";
//        });
//    }

    public Uni<String> performAsyncTask() {
        return Uni.createFrom().item(() -> {
            // Simulate a long-running operation
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Task completed successfully!";
        }).runSubscriptionOn(Infrastructure.getDefaultExecutor()); // provided by Mutiny
    }
}
