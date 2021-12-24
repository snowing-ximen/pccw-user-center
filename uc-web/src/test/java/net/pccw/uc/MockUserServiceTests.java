package net.pccw.uc;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.pccw.uc.common.dto.request.RegistrationRequest;
import net.pccw.uc.common.model.User;
import net.pccw.uc.web.UCWebApplication;
import net.pccw.uc.web.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * The type Mock springboot application tests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UCWebApplication.class)
@AutoConfigureMockMvc
public class MockUserServiceTests {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void register() throws Exception{
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("user1");
        request.setPassword("pwd1");
        request.setMobile("13110230001");
        request.setEmail("user1@mail.163");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users", request)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getUser() throws Exception{
        String username = "user1";

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + username)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }



}
