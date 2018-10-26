package com.sample.consumer.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName DcFeignClientFallbackFactory
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/10/26 17:46
 */
@Component
public class DcFeignClientFallbackFactory implements FallbackFactory<DcFeignClient> {


    @Override
    public DcFeignClient create(Throwable throwable) {
        return () -> "hello fallback"+throwable.getMessage();
    }


}
