package com.jaswine.gateway.provider;

import com.jaswine.bean.base.dto.DataRtnDTO;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 网关管理
 *
 * @author : Jaswine
 * @date : 2020-06-03 21:41
 **/
@FeignClient(value = "CLOUD-GATEWAY-MANAGE",fallback = GatewayManageFallback.class)
public interface GatewayManageProvider {

	@GetMapping(value = "/route/init")
	DataRtnDTO<List<Route>> getAllRoutes();
}
