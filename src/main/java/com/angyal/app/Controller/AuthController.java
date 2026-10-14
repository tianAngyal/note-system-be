package com.angyal.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angyal.app.Models.AuthReq;
import com.angyal.app.Models.User;
import com.angyal.app.Service.UserInfoVerificationService;
import org.springframework.web.bind.annotation.CrossOrigin;



/*@CrossOrigin(origins = "http://localhost:4200")*/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserInfoVerificationService userInfoVerificationService;

    /**
     * Prihlásenie užívateľa
     * POST /api/auth/login
     * Body: { "username": "user1", "password": "password1" }
     * Response: { "token": "jwt_token_here" }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthReq authReq) {
        try {
            String username = authReq.getUsername();
            String password = authReq.getPassword();

            // Overenie či je username a heslo vyplnené
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("Chýba meno alebo heslo!");
            }

            // Autentifikácia - vrátenie JWT tokenu
            String token = userInfoVerificationService.authenticate(username, password);

            // return ResponseEntity.ok().body(new AuthResponse(token, "Prihlásenie bolo úspešné!"));
            return ResponseEntity.ok(token);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Interná chyba servera: " + e.getMessage()));
        }
    }

    /**
     * Registrácia nového užívateľa
     * POST /api/auth/register
     * Body: { "username": "newuser", "password": "password123" }
     * Response: Vrátenie nového užívateľa s JWT tokenom
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthReq authReq) {
        try {
            String username = authReq.getUsername();
            String password = authReq.getPassword();

            // Overenie či je username a heslo vyplnené
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                return ResponseEntity.badRequest().body("Chýba meno alebo heslo!");
            }

            // Registrácia nového užívateľa
            User newUser = userInfoVerificationService.registerNewUser(username, password);

            // Generovanie JWT tokenu
            String token = userInfoVerificationService.generateToken(username);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse(newUser.getId(), newUser.getName(), token, "Registrácia bola úspešná!"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Interná chyba servera: " + e.getMessage()));
        }
    }

    /**
     * Overenie či existuje užívateľ
     * GET /api/auth/exists/{username}
     */
    @PostMapping("/exists")
    public ResponseEntity<?> checkUserExists(@RequestBody AuthReq authReq) {
        try {
            String username = authReq.getUsername();

            if (username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body("Chýba meno!");
            }

            boolean exists = userInfoVerificationService.userExists(username);

            return ResponseEntity.ok().body(new ExistsResponse(exists, username));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Interná chyba servera: " + e.getMessage()));
        }
    }

    // ==================== Inner Classes pre Response ====================

    /**
     * Response pre login
     */
    public static class AuthResponse {
        private String token;
        private String message;

        public AuthResponse(String token, String message) {
            this.token = token;
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Response pre registráciu
     */
    public static class RegisterResponse {
        private int id;
        private String username;
        private String token;
        private String message;

        public RegisterResponse(int id, String username, String token, String message) {
            this.id = id;
            this.username = username;
            this.token = token;
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Response pre overenie existencie užívateľa
     */
    public static class ExistsResponse {
        private boolean exists;
        private String username;

        public ExistsResponse(boolean exists, String username) {
            this.exists = exists;
            this.username = username;
        }

        public boolean isExists() {
            return exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    /**
     * Response pre chyby
     */
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
