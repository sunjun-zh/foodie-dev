package com.tianma.service;


import com.tianma.pojo.Items;
import com.tianma.pojo.ItemsImg;
import com.tianma.pojo.ItemsParam;
import com.tianma.pojo.ItemsSpec;
import com.tianma.pojo.vo.CommentLevelCountsVO;
import com.tianma.pojo.vo.ShopcarVO;
import com.tianma.utils.PagedGridResult;

import java.util.List;

public interface ItemService {
    /**
     * 根据商品id查询详情
     */
    public Items queryItemById(String itemId);


    /**
     * 根据商品id查询商品图片列表
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     */
    public List<ItemsSpec> queryItemsSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品评价等级数量
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价(分页)
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                             Integer page, Integer pageSize);

    /**
     * 搜搜商品列表
     */
    public PagedGridResult searchItems(String keywords, String sort,
                                      Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     */
    public PagedGridResult searchItems(Integer carId, String sort, Integer page, Integer pageSize);


    /**
     * 根据规格ids查询最新的购物车钟商品数据(用于刷新渲染购物车中的商品数据)
     */
    public List<ShopcarVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象的具体信息
     */
    public ItemsSpec queryItemsSpecById(String specId);

    /**
     * 根据商品id获取商品图片主图url
     */
    public String queryItemMainImgById(String itemId);


    /**
     * 减少库存
     */
    public void decreaseItemsSpecStock(String specId, int buyCounts);


}
