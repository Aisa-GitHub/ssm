package com.qf.pojo;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data

public class User {

	/**
	 * 涓婚敭id
	 */

	private Long id;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名位必填项!")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码为必填项!")
	private String password;

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号为必填项")
	private String phone;

	/**
	 * 创建时间
	 */
//	@NotNull
	private java.util.Date created;


}
