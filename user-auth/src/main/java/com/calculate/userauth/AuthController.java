package com.calculate.userauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            System.out.println(user.toString());
            authService.registerUser(user.getUsername(), user.getPassword());
            return new ResponseEntity<String>("User Created Successfully", new HttpHeaders(), 200);        
        } catch (Exception e) {
            String errorResponse;
            System.out.println(e.getMessage());
            switch (e.getMessage()) {
            case "ALREADY_EXIST":
                errorResponse = "A User Already Exists With The Same Username";
                break;
            case "NULL_USERNAME_OR_PASSWORD":
                errorResponse = "You Need To Specify Both Username And Password";
                break;
            default:
                errorResponse = e.getMessage();
            }
            return new ResponseEntity<String>(errorResponse, new HttpHeaders(), 400);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authService.loginUser(user.getUsername(), user.getPassword());      
            return new ResponseEntity<String>("User Logged In Successfully", new HttpHeaders(), 200);        
  
        } catch (Exception e) {
            String errorResponse;
            switch (e.getMessage()) {
            case "NOT_EXIST":
                errorResponse = "A User With The Following Username Is Not Registered";
                break;
            case "INVALID_PASSWORD":
                errorResponse = "The Password You Have Entered Is Wrong";
                break;
            case "NULL_USERNAME_OR_PASSWORD":
                errorResponse = "You Need To Specify Both Username And Password";
                break;
            default:
                errorResponse = e.getMessage();
            }
            return new ResponseEntity<String>(errorResponse, new HttpHeaders(), 400);
        }
    }
    
}
