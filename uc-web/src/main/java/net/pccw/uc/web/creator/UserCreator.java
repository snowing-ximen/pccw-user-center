package net.pccw.uc.web.creator;


import net.pccw.uc.common.dto.request.RegistrationRequest;
import net.pccw.uc.common.dto.response.UserRegistrationResponse;
import net.pccw.uc.common.model.User;

public class UserCreator {
    public static User userCreator(RegistrationRequest request){

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());

        return user;
    }

    public static UserRegistrationResponse userRegistrationResponseCreator(User user){
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword());
        response.setMobile(user.getMobile());
        response.setEmail(user.getEmail());

        return response;
    }

}