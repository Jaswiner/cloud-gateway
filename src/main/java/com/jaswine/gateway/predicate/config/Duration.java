package com.jaswine.gateway.predicate.config;

import lombok.Data;

import java.time.LocalTime;

/**
 * 时间段
 *
 * @author : Jaswine
 * @date : 2020-05-11 23:50
 **/
@Data
public class Duration {

	private LocalTime start;

	private LocalTime end;
}
