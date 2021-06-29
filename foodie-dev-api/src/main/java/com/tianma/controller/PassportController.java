package com.tianma.controller;

import com.tianma.pojo.Users;
import com.tianma.pojo.bo.UserBO;
import com.tianma.service.UserService;
import com.tianma.utils.CookieUtils;
import com.tianma.utils.JsonUtils;
import com.tianma.utils.MD5Utils;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public TIANMAJSONResult usernameIsExist(@RequestParam String username) {

        if (StringUtils.isBlank(username))
            return TIANMAJSONResult.errorMsg("用户名不能空");

        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return TIANMAJSONResult.errorMsg("用户名已经存在");
        }
        return TIANMAJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public TIANMAJSONResult regist(@RequestBody UserBO userBO,
                                   HttpServletRequest request,
                                   HttpServletResponse response
    ) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)
        )
            return TIANMAJSONResult.errorMsg("用户名或密码不能空");


        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return TIANMAJSONResult.errorMsg("用户名已经存在");
        }
        // 2. 密码长度不能少于6位
        if (password.length() < 6) {
            return TIANMAJSONResult.errorMsg("密码长度不能少于6");
        }

        // 3. 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return TIANMAJSONResult.errorMsg("两次密码输入不一致");
        }

        Users userResult = userService.createUser(userBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return TIANMAJSONResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public TIANMAJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return TIANMAJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUsersForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return TIANMAJSONResult.errorMsg("用户名或密码不正确");
        }

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);


        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return TIANMAJSONResult.ok(userResult);
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public TIANMAJSONResult logout(@RequestParam String userId,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return TIANMAJSONResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


}
