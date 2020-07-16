package com.jaswine.gateway.convert;

import com.jaswine.bean.route.RedisRouteDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;

/**
 * 自定义的RouteDefinition对象转换成RouteDefinition对象
 *
 * @see {@link org.springframework.cloud.gateway.route.RouteDefinition}
 *
 * @author : Jaswine
 * @date : 2020-07-11 18:16
 **/
public class CustomRouteDefinitionConvert {



	/**
	 * 转换方法
	 * @param redisRouteDefinition 自定义的RouteDefinition
	 * @return RouteDefinition
	 */
	public static RouteDefinition convert(RedisRouteDefinition redisRouteDefinition) {
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setId(redisRouteDefinition.getRouteId());
		routeDefinition.setUri(URI.create(redisRouteDefinition.getUri()));
		routeDefinition.setOrder(redisRouteDefinition.getOrders());
		redisRouteDefinition.getPredicates().forEach(item -> {
			PredicateDefinition predicateDefinition = new PredicateDefinition();
			BeanUtils.copyProperties(item,predicateDefinition);
			routeDefinition.getPredicates().add(predicateDefinition);
		});
		redisRouteDefinition.getFilters().forEach(item -> {
			FilterDefinition filterDefinition = new FilterDefinition();
			BeanUtils.copyProperties(item,filterDefinition);
			routeDefinition.getFilters().add(filterDefinition);
		});
		return routeDefinition;
	}
}
