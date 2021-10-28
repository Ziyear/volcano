package com.ziyear.volcano.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth implements Serializable {

    private String accessToken;
    private String refreshToken;

}
