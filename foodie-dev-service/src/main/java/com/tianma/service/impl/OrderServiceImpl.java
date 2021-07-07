package com.tianma.service.impl;

import com.tianma.enums.YesOrNo;
import com.tianma.mapper.OrderItemsMapper;
import com.tianma.mapper.OrderStatusMapper;
import com.tianma.mapper.OrdersMapper;
import com.tianma.pojo.OrderStatus;
import com.tianma.pojo.Orders;
import com.tianma.pojo.UserAddress;
import com.tianma.pojo.bo.SubmitOrderBO;
import com.tianma.pojo.vo.OrderVO;
import com.tianma.service.AddressService;
import com.tianma.service.ItemService;
import com.tianma.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private Sid sid;


    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBo) {
        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        // 包邮费设置未0
        Integer postAmount = 0;
        String orderId = sid.nextShort();
        UserAddress address = addressService.queryUserAddress(userId, addressId);

        // 1.新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity()
                + address.getDistrict()
                + address.getDetail()
        );
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        //todo 2.循环根据itemSpecIds保存订单商品信息表

        //todo 3.保存订单状表

        //todo 4.构建商户订单，用于传给支付中心

        //todo 5.构建自定义订单vo
        return null;
    }

    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return null;
    }

    @Override
    public void closeOrder() {

    }
}
