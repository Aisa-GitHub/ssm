package com.qf.service;

import com.qf.pojo.Item;
import com.qf.util.PageInfo;

public interface ItemService {
    //分页商品信息查询
    PageInfo<Item> findItemByNameLikeAndLimit(String name, Integer page, Integer size);

    Integer save(Item item);


    Integer del(Long id);
}
