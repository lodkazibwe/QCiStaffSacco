package com.qualitychemicals.qciss.security;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.loan.service.impl.LoanServiceImpl;
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

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
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

    /*@PostMapping("get/oneTime")
    public ResponseEntity<?> oneTimeAuthToken(@Valid @RequestBody OneTimeLoginDto oneTimeLoginDto){
        boolean bool=userService.isUserClosed(oneTimeLoginDto.getMobile());
        if(bool){
            throw new InvalidValuesException("profile blocked");
        }else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(oneTimeLoginDto.getMobile(), oneTimeLoginDto.getPin())
                );
            } catch (InvalidValuesException e) {
                throw new InvalidValuesException("Incorrect profile name or password");
            }
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(oneTimeLoginDto.getMobile());
            final String jwt = jwtUtil.generateToken(userDetails);

            Random random = new Random();
            String pin = String.format("%04d", random.nextInt(100000));
            userService.updatePass(oneTimeLoginDto.getMobile(), pin);

            return new ResponseEntity<>(new AuthResponse(jwt), HttpStatus.OK);
        }
    }*/

    @PutMapping("/requestPin/{contact}")//admin
    public ResponseEntity<?> requestPin(@PathVariable String contact){
        userService.requestPin(contact);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/createPass")//authenticated
    public ResponseEntity<?> createPass(@Valid @RequestBody AuthRequest authRequest){
        userService.createPass(authRequest.getPassword());
        return new ResponseEntity<>("success", HttpStatus.OK);

    }
}
