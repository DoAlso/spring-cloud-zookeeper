package com.sample.consumer.client;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author doalso
 * springcloud 允许开发人员通过@FeignClient的
 * configuration配置完全控制每一个FeignClient
 * 的私有配置
 */
@FeignClient(name = "sample-provider",fallbackFactory = DcFeignClient.DcFeignClientFallback.class,configuration = FeignClientsConfiguration.class)
public interface DcFeignClient {

    @GetMapping("/dc")
    String consumer();


    @Component
    class DcFeignClientFallback implements DcFeignClient {

        @Override
        public String consumer() {
            return "Error";
        }
    }


    @Component
    class DcFeignClientFallbackFactory implements FallbackFactory<DcFeignClient> {

        @Override
        public DcFeignClient create(Throwable throwable) {
            return () -> "hello fallback"+throwable.getMessage();
        }
    }
}
