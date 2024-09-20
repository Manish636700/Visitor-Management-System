package com.example.L10minordemo.Service;


import com.example.L10minordemo.Exception.BadRequestException;
import com.example.L10minordemo.dto.AddressDto;
import com.example.L10minordemo.dto.UserDto;
import com.example.L10minordemo.entity.Address;
import com.example.L10minordemo.entity.Flat;
import com.example.L10minordemo.entity.User;
import com.example.L10minordemo.enums.Role;
import com.example.L10minordemo.enums.Userstatus;
import com.example.L10minordemo.repo.FlatRepo;
import com.example.L10minordemo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service


public class AdminService {

        @Autowired
        private UserRepo userrepo;

        @Autowired
        private FlatRepo flatRepo;

        @Autowired
        private PasswordEncoder passwordEncoder;

    public Long create(UserDto userDto)
    {
        Flat flat = null;
        if(Role.RESIDENT.name().equals(userDto.getRole()) && userDto.getFlats()==null)
        {
            throw new BadRequestException("RESIDENT must have a flatNumber");
        }
        else
        {
           flat= flatRepo.findByNumber(userDto.getFlats());
        }
        String password = "1234";
        if(userDto.getPassword()!=null)
        {
            password = userDto.getPassword();
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .role(Role.valueOf(userDto.getRole()))
                .status(Userstatus.ACTIVE)
                .idNumber(userDto.getIdNumber())
                .password(passwordEncoder.encode(password))
                .flat(flat)
                .build();
        if(userDto.getAddressDto()!=null)
        {
            AddressDto addressDto = userDto.getAddressDto();
            Address address = Address.builder()
                    .line1(addressDto.getLine1())
                    .line2(addressDto.getLine2())
                    .pincode(addressDto.getPincode())
                    .city(addressDto.getCity())
                    .country(addressDto.getCountry())
                    .build();
            user.setAddress(address);
        }
        user=userrepo.save(user);
        return user.getId();

    }

}
