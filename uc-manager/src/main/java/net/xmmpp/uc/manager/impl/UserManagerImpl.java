package net.xmmpp.uc.manager.impl;

import net.xmmpp.uc.common.model.User;
import net.xmmpp.uc.dao.UserDAO;
import net.xmmpp.uc.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Long insert(User user) {

        Long result = userDAO.insert(user);
        return result;
    }

    @Override
    public Integer updateByUsername(User user) {
        return userDAO.updateByUsername(user);
    }

    @Override
    public User selectByUsername(String username) {
        return userDAO.selectByUsername(username);
    }

    @Override
    public Integer updatedDeletedStateByUsername(String username) {
        return userDAO.updatedDeletedStateByUsername(username);
    }


}
