package com.qf.service;

import com.qf.AcTest;
import com.qf.pojo.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class ItemServiceTest extends AcTest {

    @Autowired
    private  ItemService itemService;
//    @Test
//    public void findItemByNameLikeAndLimit() {
//        itemService.findItemByNameLikeAndLimit()
//    }

    @Test
    public void save() {
        Item item = new Item();
        item.setName("xxxx");
        item.setPrice(new BigDecimal(1.22));
        item.setProductionDate(new Date());
        item.setDescription("yyyyyyy");
        item.setPic("zzzzzzzz");

        Integer count = itemService.save(item);
        System.out.println(count);

    }
}