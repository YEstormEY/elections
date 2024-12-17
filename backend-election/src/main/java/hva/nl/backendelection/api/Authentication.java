package hva.nl.backendelection.api;

import hva.nl.backendelection.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/verifytoken")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String token) {

        if (jwtUtil.validateToken(token, jwtUtil.extractUsername(token))) {
            return ResponseEntity.ok(jwtUtil.extractUserId(token));
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }

    }

}
