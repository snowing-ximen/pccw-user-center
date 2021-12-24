package net.pccw.uc.web.controller;

import net.pccw.uc.common.dto.Response;
import net.pccw.uc.common.dto.request.RegistrationRequest;
import net.pccw.uc.common.dto.response.UserRegistrationResponse;
import net.pccw.uc.common.model.User;
import net.pccw.uc.web.creator.UserCreator;
import net.pccw.uc.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Response<Long> register(@Valid @RequestBody RegistrationRequest request) {

        User user = UserCreator.userCreator(request);
        Long result = userService.insert(user);
        Response<Long> responseEntity = new Response<>(user.getUserId());

        return  responseEntity;
    }

    @PutMapping("{username}")
    public Response<Boolean> updateUser(@PathVariable("username") String username, @Valid @RequestBody RegistrationRequest request) {
        request.setUsername(username);
        User user = UserCreator.userCreator(request);
        Integer result = userService.updateByUsername(user);
        Response<Boolean> responseEntity = new Response<>(result > 0 ? Boolean.TRUE :  Boolean.FALSE);

        return  responseEntity;
    }

    @GetMapping("{username}")
    public Response<UserRegistrationResponse> getUser(@PathVariable("username") String username) {

        User user = userService.selectByUsername(username);
        UserRegistrationResponse result = UserCreator.userRegistrationResponseCreator(user);
        Response<UserRegistrationResponse> responseEntity = new Response<>(result);

        return  responseEntity;
    }

    @DeleteMapping("{username}")
    public Response<Boolean> deregister(@PathVariable("username") String username) {

        Integer result = userService.deregisterByUsername(username);
        Response<Boolean> responseEntity = new Response<Boolean>(result > 0 ? Boolean.TRUE :  Boolean.FALSE);

        return  responseEntity;
    }
}
