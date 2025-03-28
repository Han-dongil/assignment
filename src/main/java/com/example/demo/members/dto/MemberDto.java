package com.example.demo.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class MemberDto {
	
	Integer memberNo;
	
	String memberName;
	
	String memberId;
	
	String token;
	
	String password;
	
	String role;
	
}
