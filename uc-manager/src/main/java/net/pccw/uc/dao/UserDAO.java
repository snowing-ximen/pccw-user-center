package net.pccw.uc.dao;

import org.apache.ibatis.annotations.Mapper;
import net.pccw.uc.common.model.User;

import java.util.List;


@Mapper
public interface UserDAO {

    Long insert(User user);

    Integer updateByUsername(User user);

    User selectByUsername(String username);

    Integer updatedDeletedStateByUsername(String username);
}