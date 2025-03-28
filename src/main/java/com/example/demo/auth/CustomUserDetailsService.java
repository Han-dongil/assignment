package com.example.demo.auth;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.members.dto.MemberDto;
import com.example.demo.members.mapper.MembersMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MembersMapper membersMapper;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	MemberDto dto = new MemberDto();
    	dto.setMemberId(username);
    	dto=membersMapper.getMember(dto);
    	System.err.println("!!!!");
    	System.err.println(dto);
    	System.err.println("!!!!");
        if ("hdo6513".equals(username)) {
            return new User("user", "{noop}password", Collections.emptyList());
        }
        throw new UsernameNotFoundException("User not found");
    }
}
