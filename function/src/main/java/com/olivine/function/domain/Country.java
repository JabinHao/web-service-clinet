package com.olivine.function.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    private String id;

    private String name;

    private String capital;

    private Double population;

    private Double gdp;
}
