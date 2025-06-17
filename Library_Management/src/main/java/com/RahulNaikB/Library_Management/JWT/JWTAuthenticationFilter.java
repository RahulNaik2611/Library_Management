package com.RahulNaikB.Library_Management.JWT;


import com.RahulNaikB.Library_Management.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private final JwtService jwtService;


    @Autowired
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        final String authheader = request.getHeader("Authorization");
        final String jwtToken;
        final  String username;


        //check if authorization header is present and starts with "Bearer"

        if (authheader == null  || authheader.startsWith("Bearer"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        //Extract jwt Token From header

        jwtToken = authheader.substring(7);
        username = jwtService.extractUsername(jwtToken);

        //Check if we have a username and no authentication exist yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            //Get the user details from database;

            var userDetails = userRepository.findByUsername(username)
                    .orElseThrow(()-> new RuntimeException("user not found"));


            //Validate the Token

            if(jwtService.isTokenValid (jwtToken, userDetails))
            {

                //create the authentication with user roles
                List<SimpleGrantedAuthority > authorities = userDetails.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,authorities);


                //set authentication details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                //update Security Context Authentication
                SecurityContextHolder.getContext().getAuthentication();


            }
        }
        filterChain.doFilter(request, response);

    }
}
