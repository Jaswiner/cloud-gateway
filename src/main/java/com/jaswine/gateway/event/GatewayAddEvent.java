package com.jaswine.gateway.event;

import com.lanswon.commons.bean.message.Constant;
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
@RocketMQMessageListener(topic = Constant.MSG_TOPIC_ADD_GATEWAY,consumerGroup = Constant.ROCKET_MQ_GROUP)
public class GatewayAddEvent implements RocketMQListener<String> {

	@Override
	public void onMessage(String s) {
		log.info(s);
	}

}