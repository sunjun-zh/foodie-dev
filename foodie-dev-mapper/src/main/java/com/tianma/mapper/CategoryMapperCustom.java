package com.tianma.mapper;

import com.tianma.pojo.vo.CategoryVO;
import com.tianma.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapperCustom {
    public List<CategoryVO> getSubCatList(Integer rootCatId);


    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> mao);

}