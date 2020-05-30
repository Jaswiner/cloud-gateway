package com.jaswine.gateway.bean.pojo;


import lombok.Data;

/**
 * 路由表
 *
 * @author jaswine
 */
@Data
public class Router  {


	private String routeId;
	private String uri;
	private String predicates;
	private String filters;
	private long orders;
	private String description;
	private long status;

}
