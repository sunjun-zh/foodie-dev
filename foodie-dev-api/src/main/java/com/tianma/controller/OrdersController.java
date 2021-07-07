package com.tianma.controller;

import com.tianma.pojo.bo.ShopcartBO;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "订单相关", tags= {"订单相关接口相关的api"})
@RequestMapping("orders")
public class OrdersController extends BaseController{
    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);


    @ApiOperation(value="添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public TIANMAJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO
    ){
        if(StringUtils.isBlank(userId)){
            return TIANMAJSONResult.errorMsg("");
        }
        System.out.println(shopcartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return TIANMAJSONResult.ok();

    }


    @ApiOperation(value="从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public TIANMAJSONResult add(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return TIANMAJSONResult.errorMsg("参数不能为空");
        }

        //TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        return TIANMAJSONResult.ok();

    }


}
