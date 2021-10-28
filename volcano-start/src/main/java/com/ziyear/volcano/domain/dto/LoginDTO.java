package com.ziyear.volcano.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
