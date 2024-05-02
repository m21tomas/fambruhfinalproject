package coms;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import coms.configuration.JwtUtil;
import coms.service.UserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@SpringBootTest
public class JwtTokenValidateTest {
    
    @Autowired
    private UserDetailService userDetailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    @Disabled
    @Test
    public void validateToken () {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a JWT token without Bearer part: ");
        String jwtToken = scanner.nextLine();
        String username = "";
        try {
            username = jwtUtil.extractUsername(jwtToken);
            System.out.println("JWT token extracted username: " + username);
        } catch (ExpiredJwtException e) {
            // Handle token expiration without printing stack trace
            System.out.println("Token Expired!");
            // Fail the test with an appropriate message
            Assertions.fail("Token is expired");
            scanner.close();
            return; // Exit the method to prevent further processing
        } catch (MalformedJwtException e) {
            // Handle malformed token without printing stack trace
            System.out.println("Malformed JWT token!");
            // Fail the test with an appropriate message
            Assertions.fail("Malformed JWT token");
            scanner.close();
            return; // Exit the method to prevent further processing
        } catch (Exception e) {
            // Handle other exceptions if needed
            e.printStackTrace();
        }
        
        final UserDetails userDetails = userDetailService.loadUserByUsername(username);
        
        boolean validation = jwtUtil.validateToken(jwtToken, userDetails);
        
        if(validation) {
            System.out.println("\nJWT is valid.");
            assertTrue(validation);
        }else {
            System.out.println("\nToken is invalid!");
            assertFalse(validation);
        }
        scanner.close();
    }
}