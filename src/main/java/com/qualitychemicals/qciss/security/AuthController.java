package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest){
        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
        );}catch(InvalidValuesException e){
            throw new InvalidValuesException("Incorrect user name or password");
        }
        UserDetails userDetails=myUserDetailsService.loadUserByUsername(authRequest.getUserName());

        final String jwt=jwtUtil.generateToken(userDetails);

       return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);

    }
}
