package com.qf.controller;


import static com.qf.constant.SsmConstant.*;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.util.PageInfo;
import com.qf.vo.PesultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-16 11:13
 **/
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/list")
    public String item(String name,
                         @RequestParam(value = "page",defaultValue = "1")Integer page,
                         @RequestParam(value = "size",defaultValue = "5")Integer size,
                         Model model){

        //调用service查询数据
        PageInfo pageInfo = itemService.findItemByNameLikeAndLimit(name, page, size);
        //controller将pageinfo放到request域中，将接收到的商铺内名称name也放到request域中
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);

            return "item/item_list";
    }


    @GetMapping("/add-ui")
    public String addUI(){
        return "item/item_add";
    }

    @Value("${pic_types}")
    public String picType;
    //商品添加信息
    //Request URL:http//localhost/item/add
    //Request Method:POST
    @PostMapping("/add")
    public String add(@Valid Item item, BindingResult bindingResult, MultipartFile picFile, HttpServletRequest request) throws IOException {
//         校验参数的合法性
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
//                TODD 参数不合法
            return null;
        }

        //      对图片大小做校验   要求图片小于5M
        Long size = picFile.getSize();
        //判断图片大小
          if (size > 5242880L){
//              TODO 图片过大
              return null;
          }

          boolean flag = false;

//          对图片的类型做校验    jpg.png.gif
        String[] types = picType.split(",");
        for (String type : types) {
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(),type)){
                //图片类型正确
                flag = true;
                break;
            }
        }
        if (!flag){
//          TODO  图片类型错误
            return null;
        }
//        校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
//
        if (image == null){
//        图片已损坏
            return null;
        }
//    =================================将图片保存到本地==================================
        //获取一个UUID
        String prefix = UUID.randomUUID().toString();

        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(),"");
        String newName = prefix + "." +suffix;
        //保存到本地
        String path = request.getServletContext().getRealPath("\\") + "\\static\\img\\" + newName;
        File file=new File(path);
        picFile.transferTo(file);
        //封装图片的访问路径
        String pic = "http://localhost/static/img/" + newName;

        //调用service保存商品信息
        item.setPic(pic);
        //判断
        Integer count = itemService.save(item);

        if (count == 1){
            return REDIRECT+"/item/list";
        }else {
            //TODO 添加商品信息失败、
            return null;
        }

    }

    @GetMapping("/del/{id}")
    @ResponseBody
    public PesultVO del(@PathVariable Long id ){
            //调用service 层进行删除商品
         Integer count = itemService.del(id);
        //根据结果给页面响应json
        if(count == 1){
            //删除成功
            return  new PesultVO(0,"删除商品成功",null);
        }else {
            return new PesultVO(88,"删除商品失败",null);
        }
    }



}
