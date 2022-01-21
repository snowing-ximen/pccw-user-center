package net.xmmpp.uc.web.creator;


import net.xmmpp.uc.common.dto.response.UserRegistrationResponse;
import net.xmmpp.uc.common.model.User;

public class UserRegResponseCreator {
    public static User userCreator(User user){

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword());
        response.setMobile(user.getMobile());
        response.setEmail(user.getEmail());

        return user;
    }

}