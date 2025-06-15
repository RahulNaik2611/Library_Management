package com.RahulNaikB.Library_Management.Controller;


import com.RahulNaikB.Library_Management.DTO.LoginRequestDTO;
import com.RahulNaikB.Library_Management.DTO.RegisterRequestDTO;
import com.RahulNaikB.Library_Management.Entity.User;
import com.RahulNaikB.Library_Management.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/registerNormalUser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO)
    {
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginRequestDTO>login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }


}
