package com.olivine.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/25 14:10
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    private String id;

    private String name;

    private String capital;

    private Double population;

    private Double gdp;
}
