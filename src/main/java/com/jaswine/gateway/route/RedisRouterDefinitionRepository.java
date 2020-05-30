package com.jaswine.gateway.route;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 从数据表读取数据到Redis中
 *
 * @author : Jaswine
 * @date : 2020-04-13 22:53
 **/
@Component
@Slf4j
public class RedisRouterDefinitionRepository implements RouteDefinitionRepository {

	@Value("gateway.redis.key")
	private String gatewayRoutes;

	@Resource
	private RedisTemplate<String,Object> redisTemplate;

	/**
	 * 获得路由定义
	 * @return 路由定义
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		log.info("从redis获得路由信息");

		List<RouteDefinition> routeDefinitions = new ArrayList<>();
		redisTemplate.opsForHash().values(gatewayRoutes)
				.forEach(routeDefinition ->
					routeDefinitions.add(JSON.parseObject(routeDefinition.toString(),RouteDefinition.class))
				);
		log.debug("Redis中路由信息个数为 :{} ; 详情为 : {} ",routeDefinitions.size(),routeDefinitions);
		return Flux.fromIterable(routeDefinitions);
	}


	/**
	 * 新增路由信息
	 * @param route 路由
	 * @return add
	 */
	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		log.info("保存路由信息到Redis中");

		return route.flatMap(routeDefinition -> {
			redisTemplate.opsForHash().put(gatewayRoutes,routeDefinition.getId(),JSON.toJSONString(routeDefinition));
			return Mono.empty();
		});
	}

	/**
	 * 删除路由信息
	 *
	 * @param routeId 路由id
	 * @return delete
	 */
	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		log.info("删除Redis中的路由信息");
		return routeId.flatMap(id -> {
			if (redisTemplate.opsForHash().hasKey(gatewayRoutes,id)){
				redisTemplate.opsForHash().delete(gatewayRoutes,id);
				return Mono.empty();
			}
			return Mono.defer(() -> Mono.error(new NotFoundException("路由信息没有找到："+id)));
		});
	}
}
