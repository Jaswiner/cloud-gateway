package com.jaswine.gateway.service;

import com.lanswon.commons.bean.redis.RedisKeysConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Router Service
 *
 * @author jaswine
 */
@Service
@Slf4j
public class RouteService {

	@Resource
	private RedisTemplate redisTemplate;


	private volatile Map<String,RouteDefinition> routeDefinitionMap = new LinkedHashMap<>();


	@PostConstruct
	public void loadRouteDefinition(){
		log.info("开始初始化路由配置信息,查找的Key是:{}", RedisKeysConstant.KEY_GATEWAY_ROUTES);
		Set<String> gatewayKeys = redisTemplate.keys(RedisKeysConstant.KEY_GATEWAY_ROUTES + "*");

		if (CollectionUtils.isEmpty(gatewayKeys)){
			log.info("Redis中路由信息为空");
			return;
		}

		log.info("路由初始化信息为:{}",gatewayKeys.toString());
		// 去掉前缀
		Set<String> gatewayKeyIds = gatewayKeys.stream().map(key -> {
			return key.replace(RedisKeysConstant.KEY_GATEWAY_ROUTES, StringUtils.EMPTY);
		}).collect(Collectors.toSet());
		// todo 路由信息初始化进
		routeDefinitionMap.put(null,null);

	}

	public Collection<RouteDefinition> getRouteDefinitions() {
		return routeDefinitionMap.values();
	}

	public void save(RouteDefinition routeDefinition) {
		log.info("ADD NEW RouteDefinition");
		routeDefinitionMap.put(routeDefinition.getId(),routeDefinition);
	}

	public void delete(String id) {
		log.info("DELETE RouteDefinition's ID is :{} ", id);
		routeDefinitionMap.remove(id);
	}
}
