package com.example.L10minordemo.dto;

import com.example.L10minordemo.enums.Userstatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserDto {

    @NotNull
    @Size(max=255)
    private String name;

    @NotNull
    @Size(max=255)
    private String email;

    @NotNull
    @Size(max=255)
    private String phone;

    @NotNull
    @Size(max=255)
    private String role;

    @NotNull
    private Userstatus  status;

    @NotNull
    private String idNumber;

    private String flats;

    private String password;

    private AddressDto addressDto;

}
