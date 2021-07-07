package com.tianma.mapper;

import com.tianma.pojo.vo.ItemCommentVO;
import com.tianma.pojo.vo.SearchItemsVO;
import com.tianma.pojo.vo.ShopcarVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsMapperCustom {
    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    public List<ShopcarVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    public int decreaseItemSpecStock(@Param("spcId") String specId,
                                     @Param("pendingCounts") int pendingCounts);

}
