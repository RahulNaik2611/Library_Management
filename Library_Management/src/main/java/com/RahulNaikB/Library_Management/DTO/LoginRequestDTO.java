package com.RahulNaikB.Library_Management.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO
{
    private String username;

    private String password;


}
