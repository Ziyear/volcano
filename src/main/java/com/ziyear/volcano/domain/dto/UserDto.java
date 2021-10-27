package com.ziyear.volcano.domain.dto;

import com.ziyear.volcano.validation.annotation.PasswordMatches;
import com.ziyear.volcano.validation.annotation.ValidEmail;
import com.ziyear.volcano.validation.annotation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@PasswordMatches
@Data
public class UserDto implements Serializable {
    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "用户名长度必须在4到50个字符之间")
    private String username;
    @NotNull
    @ValidPassword
    private String password;
    @NotNull
    private String matchingPassword;
    @NotNull
    @ValidEmail
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "姓名长度必须在4到50个字符之间")
    private String name;
}
