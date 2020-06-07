package com.jaswine.gateway.predicate;

import com.jaswine.gateway.predicate.config.TimeDuration;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * 时间段谓语
 *
 * @author : Jaswine
 * @date : 2020-05-11 23:49
 **/
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeDuration> {

	public TimeBetweenRoutePredicateFactory(Class<TimeDuration> configClass) {
		super(configClass);
	}

	@Override
	public Predicate<ServerWebExchange> apply(TimeDuration config) {
		return null;
	}


	/**
	 *
	 * @return
	 */
	@Override
	public List<String> shortcutFieldOrder() {
		return null;
	}
}
