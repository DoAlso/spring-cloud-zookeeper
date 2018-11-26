package com.cloud.provider.service.impl;

import com.cloud.provider.configuration.DISConfiguration;
import com.cloud.provider.dis.*;
import com.cloud.provider.service.FringeNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FringeNodeServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/13 9:59
 */
@Service
public class FringeNodeServiceImpl implements FringeNodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FringeNodeServiceImpl.class);
    @Autowired
    private DISConfiguration disProperties;
    @Autowired
    private FaceDataCollector faceDataCollector;
    @Override
    public void createFringeNode() {
        LOGGER.info("Add Fringe Node");
    }

    @Override
    public void createDeviceTemp() {
        LOGGER.info("Add Device Temp");
    }

    @Override
    public void createFringeDevice() {
        LOGGER.info("Add Fringe Device");
        EventSource eventSource = new EventSource();
        eventSource.addListener((eventObject)-> {
            LOGGER.info("开始创建调用读流的方法.....");
            LOGGER.info("This Event : {}",eventObject.getSource());
            String streamName = eventObject.getSource().toString();
            DISUtil.getInstance(disProperties);
            DISStreamPollReader pollReader = new DISStreamPollReader();
            pollReader.setDisStreamCollector(faceDataCollector);
            DISUtil.setDiStreamReader(pollReader);
            DISUtil.setStreamName(streamName);
            DISUtil.reader();
        });
        eventSource.notifyListenerEvents(new FringeNodeEvent("dis-flow"));
    }
}
