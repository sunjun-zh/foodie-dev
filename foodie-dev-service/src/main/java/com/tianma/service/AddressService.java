package com.tianma.service;

import com.tianma.pojo.UserAddress;
import com.tianma.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    /**
     * 根据用户id查询用户的收货地址列表
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     */
    public void addNewUserAddress(AddressBO addressBo);

    /**
     * 用户修改地址
     *
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id，删除对应用户地址信息
     *
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     *
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id, 查询具体的用户地址对象信息
     *
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddress queryUserAddress(String userId, String addressId);
}
