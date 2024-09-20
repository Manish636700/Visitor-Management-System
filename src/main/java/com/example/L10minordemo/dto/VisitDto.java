package com.example.L10minordemo.dto;


import com.example.L10minordemo.entity.User;
import com.example.L10minordemo.enums.VisitStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDto {

    private VisitStatus status;

    private Date inTime;

    private Date outTime;

    @NotNull
    @Size(max = 255)
    private String purpose;

    @Size(max = 255)
    private String urlOfImage;

    @NotNull
    private Integer noOfPeople;

    @NotNull
    private String idNumber;

    @NotNull
    private String flatNumber;

    private String visitorname;


}
