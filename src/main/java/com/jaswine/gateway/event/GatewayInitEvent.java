package com.jaswine.gateway.event;

import com.jaswine.bean.message.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author : Jaswine
 * @date : 2020-05-18 01:26
 **/
@Service
@Slf4j
@RocketMQMessageListener(topic = MessageConstant.MSG_TOPIC_INIT_GATEWAY,consumerGroup = MessageConstant.ROCKET_MQ_GROUP)
public class GatewayInitEvent implements RocketMQListener<String> {

	@Override
	public void onMessage(String s) {
		log.info(s);
	}

}