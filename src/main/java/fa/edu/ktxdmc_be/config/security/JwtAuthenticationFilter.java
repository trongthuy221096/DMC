package fa.edu.ktxdmc_be.config.security;

import fa.edu.ktxdmc_be.common.UserDetailsImpl;
import fa.edu.ktxdmc_be.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

//        try {
//            Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception exp) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            PrintWriter writer = httpResponse.getWriter();
//            writer.print(exp.getMessage());
//            writer.flush();
//            writer.close();
//        }

        String jwtToken = null;
        String phoneNumber = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7);
            try {
                phoneNumber = jwtAuthenticationProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token is expired");
            }
        }

        if (phoneNumber != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(phoneNumber);
            //check jwt token
            if (jwtAuthenticationProvider.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);
    }
}