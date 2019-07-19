package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-16 11:37
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public PageInfo<Item> findItemByNameLikeAndLimit(String name, Integer page, Integer size) {
//        查询商品数据总条数
        Long total = itemMapper.findCountByName(name);
//        初始化PageInfo
        PageInfo<Item> pageInfo = new PageInfo<>(page,total,size);
//        查询具体商品的信息
        List<Item> list = itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffset(), pageInfo.getSize());
//        将查询的商品信息set到PageInfo中
        pageInfo.setList(list);
//        返回pageInfo
        return pageInfo;
    }

    @Override
    @Transactional
    public Integer save(Item item) {
        //添加商品信息
        Integer save = itemMapper.save(item);

        return save;
    }

    @Override
    @Transactional
    public Integer del(Long id) {
          Integer del= itemMapper.del(id);
          return del;
    }


}
