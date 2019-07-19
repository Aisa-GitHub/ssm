package com.qf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-17 12:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PesultVO {
   private Integer code;

   private String msg;

   private Object data;
}
