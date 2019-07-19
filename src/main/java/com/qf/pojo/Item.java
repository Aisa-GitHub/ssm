package com.qf.pojo;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	/**
	 * 涓婚敭id
	 */

	private Long id;

	/**
	 * 商品名称
	 */
	@NotBlank(message = "商品名称位必填项!")
	private String name;

	/**
	 * 商品价格
	 */
	@NotNull(message = "商品价格为必填项!")
	private BigDecimal price;

	/**
	 * 生产日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "商品日期是必填项!")
	private Date productionDate;

	/**
	 * 商品描述
	 */
	@NotBlank(message = "请填写商品描述!")
	private String description;

	/**
	 * 商品图片路径
	 */
	private String pic;

	/**
	 * 创建时间
	 */
	private Date created;


}
