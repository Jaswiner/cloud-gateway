package com.jaswine.gateway.route;

import com.jaswine.bean.redis.RedisKeysConstant;
import com.jaswine.bean.route.RedisRouteDefinition;
import com.jaswine.gateway.convert.CustomRouteDefinitionConvert;
import com.jaswine.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.synchronizedMap;

/**
 * 从数据表读取数据到Redis中
 *
 * @author : Jaswine
 * @date : 2020-04-13 22:53
 **/
@Component
@Slf4j
public class RedisRouterDefinitionRepository implements RouteDefinitionRepository {

	/** Routes */
	private final Map<String,RouteDefinition> routeDefinitions = synchronizedMap(new LinkedHashMap<>());

	@Resource
	private RedisTemplate<String,Object> redisTemplate;




	/**
	 * 获得路由定义
	 * @return 路由定义
	 */
	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		log.info("从redis获得所有路由信息");
		List<RedisRouteDefinition> routes = ((List<String>) (List) redisTemplate.opsForHash().values(RedisKeysConstant.KEY_GATEWAY_ROUTES)).stream()
				.map(item -> JsonUtil.json2Obj(item, RedisRouteDefinition.class))
				.collect(Collectors.toList());

		routes.forEach(route -> {
			log.info("新增路由信息");
			routeDefinitions.put(route.getRouteId(),CustomRouteDefinitionConvert.convert(route));
		});

		log.info("Redis中路由信息个数为 : {} ; 详情为 : {} ",routeDefinitions.size(),routeDefinitions);
		return Flux.fromIterable(routeDefinitions.values());
	}


	/**
	 * 新增路由信息
	 * @param route 路由
	 * @return add
	 */
	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {

		return route.flatMap(r -> {
			if (StringUtils.isEmpty(r.getId())) {
				return Mono.error(new IllegalArgumentException("id may not be empty"));
			}
			routeDefinitions.put(r.getId(),r);
			return Mono.empty();
		});
	}

	/***
	 * 保存Route
	 * @param route 路由定义
	 */
	public void save(RouteDefinition route){
		if (StringUtils.isEmpty(route.getId())) {
			throw new IllegalArgumentException("id may not be empty");
		}
		routeDefinitions.put(route.getId(),route);
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
			if (routeDefinitions.containsKey(id)) {
				routeDefinitions.remove(id);
				return Mono.empty();
			}
			return Mono.defer(() -> Mono.error(
					new NotFoundException("RouteDefinition not found: " + routeId)));
		});
	}

	public void delete(String id){

		if (routeDefinitions.containsKey(id)) {
			routeDefinitions.remove(id);
		}else {
			throw new NotFoundException("RouteDefinition not found: " + id);
		}
	}

}