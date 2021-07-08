package com.tianma.controller;

import com.tianma.enums.OrderStatusEnum;
import com.tianma.enums.PayMethod;
import com.tianma.pojo.OrderStatus;
import com.tianma.pojo.bo.ShopcartBO;
import com.tianma.pojo.bo.SubmitOrderBO;
import com.tianma.pojo.vo.MerchantOrdersVO;
import com.tianma.pojo.vo.OrderVO;
import com.tianma.service.OrderService;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpHandler;


@Api(value = "订单相关", tags = {"订单相关接口相关的api"})
@RequestMapping("orders")
public class OrdersController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public TIANMAJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return TIANMAJSONResult.errorMsg("支付方式不支持!");
        }
        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2.创建订单以后，移除购物车中已结算(已提交)的商品
        // todo 整合redis之后，完善购物车中的已结算商品清算，并且同步到前端cookie

        // 3.向支付中心(自定义)发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        // todo 关于支付返回url payReturnUrl
        String payReturnUrl = "";
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        //todo 方便测试，统一改为1分钱
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("userId", "xxx");
        headers.add("password", "xxx");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<TIANMAJSONResult> responseEntity = restTemplate.postForEntity(payReturnUrl,
                entity,
                TIANMAJSONResult.class);
        TIANMAJSONResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            logger.error("发送错误： {}", paymentResult.getMsg());
            return TIANMAJSONResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }
        return TIANMAJSONResult.ok(orderId);
    }


    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public TIANMAJSONResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return TIANMAJSONResult.ok();
    }


}
