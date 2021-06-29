package com.tianma.service;


import com.tianma.pojo.Category;
import com.tianma.pojo.vo.CategoryVO;
import com.tianma.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有一级分类
     */
    public List<Category> queryAllRootLevelCat();


    /**
     * 根据一级分类id查询分类信息
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);


}
