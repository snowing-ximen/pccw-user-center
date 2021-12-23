package net.pccw.uc.web.creator;


import net.pccw.uc.common.dto.request.RegistrationRequest;
import net.pccw.uc.common.dto.response.UserRegistrationResponse;
import net.pccw.uc.common.model.User;

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