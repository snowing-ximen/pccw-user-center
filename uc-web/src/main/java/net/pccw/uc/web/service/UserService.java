package net.pccw.uc.web.service;

import net.pccw.uc.common.model.User;

public interface UserService {

     Long insert(User user);

     Integer updateByUsername(User user);

     User selectByUsername(String username);

     Integer deregisterByUsername(String username);

}
