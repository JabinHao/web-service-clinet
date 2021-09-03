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
public class User {

    private String id;

    private String name;

    private Integer age;
}
