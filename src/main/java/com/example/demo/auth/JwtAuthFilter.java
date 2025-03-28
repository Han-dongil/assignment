package com.example.demo.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 로그인 경로는 필터를 거치지 않도록 예외 처리
        if ("/api/v1/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // 토큰 추출
        String token = extractToken(request);

        // 토큰이 없거나 유효하지 않으면 인증 절차를 건너뜀
        if (token == null || token.isEmpty() || !jwtUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자 이름 추출
        String username = jwtUtil.extractUsername(token);
        if (username == null || username.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        // UserDetailsService를 통해 사용자 정보 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 인증 정보 세팅
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
    }
}
