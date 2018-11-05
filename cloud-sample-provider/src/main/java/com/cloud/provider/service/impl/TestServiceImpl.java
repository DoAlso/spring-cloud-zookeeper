package com.cloud.provider.service.impl;

import com.cloud.provider.service.TestService;
import com.huaweicloud.dis.iface.data.response.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/1 11:42
 */
@Service
public class TestServiceImpl implements TestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);
    @Override
    public void doInsert(Record record) {
        LOGGER.info("Get record [{}], partitionKey [{}], sequenceNumber [{}],threadName [{}].",
                new String(record.getData().array()),
                record.getPartitionKey(),
                record.getSequenceNumber(),
                Thread.currentThread().getName());
    }
}
