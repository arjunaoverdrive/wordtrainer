package org.arjunaoverdrive.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

// TODO: 21.10.2022
//  add logic for adding other sets words
//  enable delete buttons on the Correct Mistakes page
//  add logic to persist practice mistaken words results ???
//  add logic to upload a file to import words