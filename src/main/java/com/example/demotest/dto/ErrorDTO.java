package com.example.demotest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String campo;
    private String message;
}
