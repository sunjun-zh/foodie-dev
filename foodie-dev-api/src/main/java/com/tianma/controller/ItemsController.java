package com.tianma.controller;


import com.tianma.pojo.*;
import com.tianma.pojo.vo.*;

import com.tianma.service.ItemService;
import com.tianma.utils.PagedGridResult;
import com.tianma.utils.TIANMAJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tianma.controller.BaseController.COMMON_PAGE_SIZE;
import static com.tianma.controller.BaseController.PAGE_SIZE;

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController {
    @Autowired
    private ItemService itemService;


    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public TIANMAJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId
    ) {
        if (StringUtils.isBlank(itemId)) {
            return TIANMAJSONResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemsSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        // 组装商品信息vo
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemsImgList(itemsImgList);
        itemInfoVO.setItemsSpecList(itemsSpecList);
        itemInfoVO.setItemsParam(itemsParam);
        return TIANMAJSONResult.ok(itemInfoVO);
    }


    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public TIANMAJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId
    ) {
        if (StringUtils.isBlank(itemId)) {
            return TIANMAJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return TIANMAJSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public TIANMAJSONResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @PathVariable Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @PathVariable Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @PathVariable Integer pageSize
    ) {
        if (StringUtils.isBlank(itemId)) {
            return TIANMAJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return TIANMAJSONResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public TIANMAJSONResult search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @PathVariable String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @PathVariable String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @PathVariable Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @PathVariable Integer pageSize
    ) {
        if (StringUtils.isBlank(keywords)) {
            return TIANMAJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return TIANMAJSONResult.ok(grid);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public TIANMAJSONResult catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @PathVariable String catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @PathVariable String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @PathVariable Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @PathVariable Integer pageSize
    ) {
        if (catId == null) {
            return TIANMAJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);
        return TIANMAJSONResult.ok(grid);
    }

    // 用于用户长时间未登录网站，刷新购物车中的数据(主要是商品价格)，类似京东淘宝
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public TIANMAJSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
            @PathVariable String itemSpecIds
    ) {
       if(StringUtils.isBlank(itemSpecIds)){
           return TIANMAJSONResult.ok();
       }

        List<ShopcarVO> list = itemService.queryItemsBySpecIds(itemSpecIds);
        return TIANMAJSONResult.ok(list);
    }


}
