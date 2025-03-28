package com.example.demo.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.members.dto.MemberDto;

@Mapper
public interface MembersMapper {
	
	MemberDto getMember(MemberDto memberDto);

}
