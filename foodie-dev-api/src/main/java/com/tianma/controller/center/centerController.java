package com.tianma.controller.center;

import com.tianma.pojo.Users;
import com.tianma.service.center.CenterUserService;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center-用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("center")
public class centerController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    public TIANMAJSONResult userInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId
    ) {
        Users user = centerUserService.queryUserInfo(userId);
        return TIANMAJSONResult.ok(user);
    }
}
