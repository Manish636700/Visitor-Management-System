package com.example.L10minordemo.Controller;


import com.example.L10minordemo.Exception.BadRequestException;
import com.example.L10minordemo.Service.AdminService;
import com.example.L10minordemo.dto.AddressDto;
import com.example.L10minordemo.dto.UserDto;
import com.example.L10minordemo.enums.Userstatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/Create")
    ResponseEntity<Long>createUser(@RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(adminService.create(userDto));

    }
    @PostMapping("/user-csv-upload")
    public ResponseEntity<List<String>>createUsersWithCSV(@RequestParam("file") MultipartFile file) {
        logger.info("File Uploaded: {} ",file.getOriginalFilename());

        List<String>responce = new ArrayList<>();
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord>csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords)
            {
                UserDto userDto = new UserDto();
                userDto.setName(csvRecord.get("name"));
                userDto.setEmail(csvRecord.get("email"));
                userDto.setPhone(csvRecord.get("phone"));
                userDto.setFlats(csvRecord.get("flats"));
                userDto.setRole(csvRecord.get("role"));
                userDto.setIdNumber(csvRecord.get("idNumber"));
                userDto.setStatus(Userstatus.ACTIVE);
                AddressDto addressDto = new AddressDto();
                addressDto.setLine1(csvRecord.get("line1"));
                addressDto.setLine2(csvRecord.get("line2"));
                addressDto.setCity(csvRecord.get("city"));
                addressDto.setPincode(csvRecord.get("pincode"));
                addressDto.setCountry(csvRecord.get("country"));
                userDto.setAddressDto(addressDto);

                try{
                    Long id = adminService.create(userDto);
                    responce.add("Created User "+userDto.getName()+"with id:"+id);
                }
                catch (Exception e)
                {
                    responce.add("Unable to created User" + userDto.getName()+"msg:" + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {

        }
        return ResponseEntity.ok(responce);

    }

}
