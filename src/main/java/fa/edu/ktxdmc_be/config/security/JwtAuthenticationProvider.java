package fa.edu.ktxdmc_be.config.security;

import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Component

public class JwtAuthenticationProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expirationDateInMs}")
    private int expiresIn;
    @Value("${jwt.authorities.key}")
    private String AUTHORITIES_KEY;
    @Value("${jwt.username.key}")
    private String USERNAME_KEY;
    @Value("${jwt.id.key}")
    private String ID_KEY;
    @Value("${jwt.roomid.key}")
    private String ROOMID_KEY;
    @Value("${jwt.cookieName}")
    private String jwtCookie;
    @Value("${urlPath}")
    private String urlPath;

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateToken(UserDetailsImpl userDetails) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String role = userDetails.getAuthorities().toString();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, role)
                .claim(USERNAME_KEY, userDetails.getName())
                .claim(ID_KEY, userDetails.getUserId())
                .claim(ROOMID_KEY, userDetails.getRoomId())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L);
    }

    public Boolean validateToken(String token, UserDetailsImpl userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    public boolean isTokenExpired(String token) {
        Date expireDate = getExpirationDate(token);
        return expireDate.before(new Date());
    }


    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }


}
