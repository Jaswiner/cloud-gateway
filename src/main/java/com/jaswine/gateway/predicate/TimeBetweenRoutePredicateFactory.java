package com.jaswine.gateway.predicate;

import com.lanswon.gateway.predicate.config.Duration;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author : Jaswine
 * @date : 2020-05-11 23:49
 **/
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<Duration> {

	public TimeBetweenRoutePredicateFactory(Class<Duration> configClass) {
		super(configClass);
	}

	@Override
	public Predicate<ServerWebExchange> apply(Duration config) {
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
