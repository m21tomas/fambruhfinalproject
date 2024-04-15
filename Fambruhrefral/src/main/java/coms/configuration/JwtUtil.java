package coms.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import coms.exceptions.TokenValidationTimeException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
	
	private String SECRET_KEY = "medicare";

	public static final long TWO_MINUTES_VALIDITY = 1*1*2*60; //days*hours*minutes*seconds
	
	public static final long TEN_MINUTES_VALIDITY = 1*1*10*60; //days*hours*minutes*seconds

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    private String extractEmailFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    public String generateVerifyAccountToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return create2MinToken(claims, userDetails.getUsername());
    }
    
    public String generatePasswordResetToken(String email) {
    	Map<String, Object> claims = new HashMap<>();
        return create10MinToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    private String create2MinToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TWO_MINUTES_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    private String create10MinToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TEN_MINUTES_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
        	final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
        	String myStr = " Email link time for the account verification expired. "
    				+ "Generate a new email link for your account verification.";
            throw new TokenValidationTimeException(e.getMessage()+myStr);
        }
    }

    public boolean validateTokenForEmail(String token, String email) {
        try {
            final String tokenEmail = extractEmailFromToken(token);
            return (tokenEmail.equals(email) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            String errorMessage = "Email link time for the password reset expired. \n "
                    + "Generate a new email link for your password reset.";
            throw new TokenValidationTimeException(e.getMessage() + errorMessage);
        }
    }
}
