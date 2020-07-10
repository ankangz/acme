package com.ankang.acme.user.dal.persistence;

import com.ankang.acme.user.dal.entity.User;

public interface UserMapper {
    /**
     * 
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);
    
}
