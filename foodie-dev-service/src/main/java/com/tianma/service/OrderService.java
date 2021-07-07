package com.tianma.service;

import com.tianma.pojo.OrderStatus;
import com.tianma.pojo.bo.SubmitOrderBO;
import com.tianma.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBo
     * @return
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBo);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     */
    public OrderStatus queryOrderStatusInfo(String orderId);


    /**
     * 关闭超时未支付订单
     */
    public void closeOrder();
}
