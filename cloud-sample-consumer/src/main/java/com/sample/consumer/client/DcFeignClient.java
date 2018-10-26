package com.sample.consumer.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author doalso
 * springcloud 允许开发人员通过@FeignClient的
 * configuration配置完全控制每一个FeignClient
 * 的私有配置
 */
@FeignClient(name = "sample-provider",fallback = DcFeignClientFallback.class,fallbackFactory = DcFeignClientFallbackFactory.class)
public interface DcFeignClient {

    @GetMapping("/dc")
    String consumer();
}
