package com.jaswine.gateway.event;

import com.jaswine.bean.message.MessageConstant;
import com.jaswine.bean.route.RedisRouteDefinition;
import com.jaswine.gateway.convert.CustomRouteDefinitionConvert;
import com.jaswine.gateway.route.RedisRouterDefinitionRepository;
import com.jaswine.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.gateway.support.NotFoundException;

import javax.annotation.Resource;

/**
 * @author : Jaswine
 * @date : 2020-07-16 20:54
 **/
@Slf4j
public class GatewayEvent {

	@Resource
	private RedisRouterDefinitionRepository routerDefinitionRepository;

	@RabbitListener(queuesToDeclare = @Queue(value = MessageConstant.MSG_TOPIC_ADD_GATEWAY,durable = "true"))
	public void addGatewayEvent(String s){
		log.info("新增的的路由信息 : {}",s);
		// 0.Json转换
		RedisRouteDefinition redisRouteDefinition = JsonUtil.json2Obj(s, RedisRouteDefinition.class);
		// 2.更新路由信息
		routerDefinitionRepository.save(CustomRouteDefinitionConvert.convert(redisRouteDefinition));
	}

	@RabbitListener(queuesToDeclare = @Queue(value = MessageConstant.MSG_TOPIC_DELETE_GATEWAY,durable = "true"))
	public void deleteGatewayEvent(String id){
		log.info("删除的的路由ID : {}",id);
		// 2.更新路由信息
		try {
			routerDefinitionRepository.delete(id);
		}catch (NotFoundException e){

		}
	}

	@RabbitListener(queuesToDeclare = @Queue(value = MessageConstant.MSG_TOPIC_UPDATE_GATEWAY,durable = "true"))
	public void updateGatewayEvent(String s){
		log.info("更新的路由信息 : {}",s);
		// 0.Json转换
		RedisRouteDefinition redisRouteDefinition = JsonUtil.json2Obj(s, RedisRouteDefinition.class);
		// 2.更新路由信息
		routerDefinitionRepository.save(CustomRouteDefinitionConvert.convert(redisRouteDefinition));
	}




}
