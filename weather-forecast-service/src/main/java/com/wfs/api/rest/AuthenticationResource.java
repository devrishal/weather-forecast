package com.wfs.api.rest;

import com.wfs.api.swagger.AuthenticationResponse;
import com.wfs.security.request.LoginRequest;
import com.wfs.security.response.LoginResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AuthenticationResource {
    @Operation(summary = "Login to the application")
    @AuthenticationResponse
    @RequestMapping(method = RequestMethod.POST, path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    ResponseEntity<LoginResult> doLogin(@ModelAttribute LoginRequest loginRequest);
}