package net.pccw.uc.manager;

import net.pccw.uc.common.model.User;

public interface UserManager {

    Long insert(User user);

    Integer updateByUsername(User user);

    User selectByUsername(String username);

    Integer updatedDeletedStateByUsername(String username);

}
