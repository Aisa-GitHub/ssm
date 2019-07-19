package com.qf.mapper;

import com.qf.AcTest;
import com.qf.pojo.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ItemMapperTest extends AcTest {
    @Autowired
    private ItemMapper itemMapper;


    @Test
    public void findCountByName() {
        Long count = itemMapper.findCountByName(null);
        System.out.println(count);
    }

    @Test
    public void findItemByNameLikeAndLimit() {
        List<Item> list = itemMapper.findItemByNameLikeAndLimit(null, 0, 5);
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void save() {
        Item item = new Item();
        item.setName("xxxx");
        item.setPrice(new BigDecimal(1.22));
        item.setProductionDate(new Date());
        item.setDescription("yyyyyyy");
        item.setPic("zzzzzzzz");

        Integer count = itemMapper.save(item);
        System.out.println(count);

    }

    @Test
    @Transactional
    public void del(){
        Integer  count = itemMapper.del(14L);

        System.out.println(count);
    }
}