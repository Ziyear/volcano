package com.ziyear.volcano.domain.dto;

import com.ziyear.volcano.validation.annotation.ValidPassword;
import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
