package com.tianma.controller;

import com.tianma.pojo.UserAddress;
import com.tianma.pojo.bo.AddressBO;
import com.tianma.pojo.bo.ShopcartBO;
import com.tianma.service.AddressService;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.utils.MobileEmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "地址相关", tags = {"地址相关接口相关的api"})
@RequestMapping("address")
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public TIANMAJSONResult list(
            @RequestParam String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            return TIANMAJSONResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return TIANMAJSONResult.ok(list);
    }


    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public TIANMAJSONResult add(
            @RequestBody AddressBO addressBO
    ) {
        TIANMAJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return TIANMAJSONResult.ok();

    }

    private TIANMAJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return TIANMAJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return TIANMAJSONResult.errorMsg("收货人姓名不能太长");
        }
        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return TIANMAJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return TIANMAJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOK = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOK) {
            return TIANMAJSONResult.errorMsg("收货人手机号格式不正确");
        }
        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)
        ) {
            return TIANMAJSONResult.errorMsg("收货地址信息不能为空");
        }
        return TIANMAJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public TIANMAJSONResult update(
            @RequestBody AddressBO addressBO
    ) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return TIANMAJSONResult.errorMsg("修改地址错误：addressId不能为空");
        }
        TIANMAJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.updateUserAddress(addressBO);
        return TIANMAJSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public TIANMAJSONResult delete(
            @RequestParam String userId,
            @RequestParam String addressId
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userId)) {
            return TIANMAJSONResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId, addressId);
        return TIANMAJSONResult.ok();
    }


    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public TIANMAJSONResult setDefault(
            @RequestParam String userId,
            @RequestParam String addressId
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userId)) {
            return TIANMAJSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return TIANMAJSONResult.ok();
    }
}
