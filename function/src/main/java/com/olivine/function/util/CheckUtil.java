package com.olivine.function.util;

import com.olivine.function.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @description:
 * @author: jphao
 * @date: 2021/8/24 16:28
 */
@Slf4j
public class CheckUtil {

    public static boolean checkCountry(Country country){
        if (!StringUtils.hasText(country.getName())){
            log.error("Country name cannot be null");
            throw new IllegalArgumentException("illegal Country name!");
        }

        if (country.getPopulation() < 0){
            log.error("population is negative");
            throw new IllegalArgumentException("population must be positive");
        }

        return true;
    }
}
