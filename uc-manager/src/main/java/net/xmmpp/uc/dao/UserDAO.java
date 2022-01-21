package net.xmmpp.uc.dao;

import org.apache.ibatis.annotations.Mapper;
import net.xmmpp.uc.common.model.User;


@Mapper
public interface UserDAO {

    Long insert(User user);

    Integer updateByUsername(User user);

    User selectByUsername(String username);

    Integer updatedDeletedStateByUsername(String username);
}