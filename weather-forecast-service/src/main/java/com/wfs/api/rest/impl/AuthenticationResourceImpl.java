package com.wfs.api.rest.impl;

import com.wfs.api.rest.AuthenticationResource;
import com.wfs.security.config.JwtHelper;
import com.wfs.security.config.WebSecurityConfig;
import com.wfs.security.request.LoginRequest;
import com.wfs.security.response.LoginResult;
import com.wfs.utility.exception.WeatherForecastException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthenticationResourceImpl implements AuthenticationResource {
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<LoginResult> doLogin(LoginRequest loginRequest) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new WeatherForecastException("User not found", 401);
        }

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", loginRequest.getUsername());

            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
            claims.put("userId", String.valueOf(1));

            String jwt = jwtHelper.createJwtForClaims(loginRequest.getUsername(), claims);
            return new ResponseEntity<>(new LoginResult(jwt), HttpStatus.OK);
        }else{
            throw new WeatherForecastException("Wrong Credentials",401);
        }
    }
}
