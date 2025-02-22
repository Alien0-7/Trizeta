package org.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/newuser")
    public ResponseEntity<String> receiveData(@RequestBody String jsonBody) {
        System.out.println("Corpo JSON ricevuto: \n" + jsonBody);

        // Rispondi con un messaggio di successo
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/plain")
                .body("Dati ricevuti con successo!");
    }
}
