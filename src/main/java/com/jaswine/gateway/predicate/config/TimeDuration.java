package com.jaswine.gateway.predicate.config;

import lombok.Data;

import java.time.LocalTime;

/**
 * 时间段(时:分:秒)
 *
 * @author : Jaswine
 * @date : 2020-05-11 23:50
 **/
@Data
public class TimeDuration {

	/** 开始时间 */
	private LocalTime start;

	/** 结束时间 */
	private LocalTime end;
}
