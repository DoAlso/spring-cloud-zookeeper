package com.cloud.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @author Hyx
 */
public interface DIStreamReader {

   /**
    * 策略模式读取DIS
    * 中的流
    * @param dis
    * @param streamName
    */
   void reader(DIS dis,String streamName);

}
