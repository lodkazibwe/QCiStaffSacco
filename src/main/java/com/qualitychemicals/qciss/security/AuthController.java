package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(origins = {"https://qcstaffsacco.com"}, allowedHeaders = "*")
@CrossOrigin()
@RestController
@RequestMapping("/authenticate")
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired UserService userService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("get/token")
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody AuthRequest authRequest){
        boolean bool=userService.isUserClosed(authRequest.getUserName());
        if(bool){
            throw new InvalidValuesException("profile blocked");
        }else {
            try {
                logger.info("authenticating....");
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
                );
            } catch (Exception e) {
                logger.info("invalid user name or pass....");
                throw new InvalidValuesException("Incorrect profile name or password");

            }
            logger.info("getting user details....");
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUserName());

            final String jwt = jwtUtil.generateToken(userDetails);
            logger.info("authenticated....");

            return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
        }

    }

    @PostMapping("get/authToken")
    public ResponseEntity<?> createToken(@Valid @RequestBody AuthRequest authRequest){
        boolean bool=userService.isUserClosed(authRequest.getUserName());
        if(bool){
            throw new InvalidValuesException("profile blocked");
        }else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
                );
            } catch (InvalidValuesException e) {
                throw new InvalidValuesException("Incorrect profile name or password");
            }
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUserName());
            AuthResp authResponse=myUserDetailsService.getResponse(authRequest.getUserName());


            final String jwt = jwtUtil.generateToken(userDetails);


            return new ResponseEntity<>(new AuthResp(jwt,authResponse.getName(),authResponse.getRole()), HttpStatus.OK);
        }

    }


    @PutMapping("/requestPin/{contact}")//admin
    public ResponseEntity<?> requestPin(@PathVariable String contact){
        userService.requestPin(contact);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/createPass")//authenticated
    public ResponseEntity<?> createPass(@Valid @RequestBody ChangePassRequest changePassRequest){
        AuthRequest authRequest =new AuthRequest();
        authRequest.setPassword(changePassRequest.getOldPassword());
        authRequest.setUserName(changePassRequest.getUserName());
        ResponseEntity<?> response =createAuthToken(authRequest);
        String userName =myUserDetailsService.currentUser();

        if(response.getStatusCode()==HttpStatus.OK && userName.equals(changePassRequest.getUserName())){
            userService.createPass(changePassRequest.getNewPassword());
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        throw new InvalidValuesException("password change failed");

    }

    @PutMapping("/get/newPin")
    public ResponseEntity<?> resetPass(@Valid @RequestBody ResetPassRequest resetPassRequest){
        myUserDetailsService.resetPassword(resetPassRequest);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
