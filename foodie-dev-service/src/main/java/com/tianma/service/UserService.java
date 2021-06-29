package com.tianma.service;

import com.tianma.pojo.Users;
import com.tianma.pojo.bo.UserBO;

public interface UserService {

    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);


    public Users createUser(UserBO userBO) throws Exception;

    public Users queryUsersForLogin(String username, String password);

}
