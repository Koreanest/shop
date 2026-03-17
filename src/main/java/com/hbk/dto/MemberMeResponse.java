package com.hbk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberMeResponse {
    private Long memberId;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String tel;
    private String zip;
    private String address1;
    private String address2;
}