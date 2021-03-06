package com.ziyear.volcano.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecuredRestAPIIntTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @WithMockUser
    @Test
    public void givenAuthRequest_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/api/me").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"USER"})
//    @WithMockUser(username = "ZHANGSAN", roles = {"USER"})
    @Test
    public void givenRoleUserOrAdmin_thenAccessSuccess() throws Exception {
        mvc.perform(get("/api/users/{username}", "user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "lisi")
//    @WithMockUser(username = "zhangsan")
    @Test
    public void givenUserRole_whenQueryUserByEmail_shouldSuccess() throws Exception {
        mvc.perform(get("/api/users/email/{email}", "zs@local.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "lisi",roles = "USER")
//    @WithMockUser(username = "lisi",roles = "ADMIN")
    @Test
    public void managerTest() throws Exception {
        mvc.perform(get("/api/users/manager"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
