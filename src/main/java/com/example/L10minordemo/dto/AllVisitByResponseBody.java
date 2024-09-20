package com.example.L10minordemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllVisitByResponseBody {

    private List<VisitDto> visits;

    private Long totalrow;

    private Integer totalPage;
}
