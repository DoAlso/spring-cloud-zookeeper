package com.sample.consumer.client;

import org.springframework.stereotype.Component;

/**
 * @ClassName DcFeignClientFallback
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/10/26 17:46
 */
@Component
public class DcFeignClientFallback implements DcFeignClient {

    @Override
    public String consumer() {
        return "Error";
    }
}
