package com.kainoss.exchange.dto;

import lombok.Data;

@Data
public class CurrencyDto {

    private Long id;
    private String code;
    private String toCode;
}
