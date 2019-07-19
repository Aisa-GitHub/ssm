package com.qf.util;

import com.qf.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-16 11:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {

    //商品分析和分页信息
    //当前页
    private Integer page;
    //每页显示条数
    private  Integer size;
    private Long total;
    //计算总页数
    private Integer  pages; //total/size
    //计算起始索引
    private Integer offset;//(page - 1) * size;
    //商品信息
    private List<T> list;


    public PageInfo(Integer page, Long total, Integer size) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 5 :size;
        this.total = total < 0 ? 0 :total;
        //计算出pages和offset
        this.pages =(int)(total % size == 0 ? this.total / this.size :  this.total / this.size + 1);
        this.offset = (this.page - 1) * this.size;
    }
}
