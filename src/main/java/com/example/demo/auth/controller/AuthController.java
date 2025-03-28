package com.example.demo.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.members.dto.MemberDto;
import com.example.demo.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "User", description = "Operations related to users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Get user by ID",
            description = "Fetch user details by providing user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
	@PostMapping(value="/api/v1/login")
	public String login (@RequestBody MemberDto dto) throws Exception{
		System.err.println(dto);
		System.err.println("!!!!@");
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getMemberId(), dto.getPassword()));
        return jwtUtil.generateToken(dto.getMemberId());
	}
	
	
}
