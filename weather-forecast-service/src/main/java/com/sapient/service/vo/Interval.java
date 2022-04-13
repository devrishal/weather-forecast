package com.sapient.service.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;

}
