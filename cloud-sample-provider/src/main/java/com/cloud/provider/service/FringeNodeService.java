package com.cloud.provider.service;

/**
 * @ClassName FringeNodeService
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/13 9:53
 */
public interface FringeNodeService {

    /**
     * 创建边缘节点
     */
    void createFringeNode();


    /**
     * 创建设备模板：
     * 可以对一类设备定义统一的模板，具备一致的设备属性，
     * 便于批量创建设备。比如您可以创建一个自定义的设备模板“Camera”，
     * 创建设备时可以使用该设备模板的所有属性，而不必为每一个类似的设备设置同样的属性。
     */
    void createDeviceTemp();


    /**
     * 创建边缘设备：边缘设备是边缘侧真实设备的映射，
     * 创建边缘设备可以用来监测和管理边缘侧真实设备的属性和状态。
     * 而位于边缘侧的设备，可以小到传感器、控制器，
     * 大到智能摄像机或工控机床。
     * 智能边缘计算服务中提供对边缘设备的管理、控制和数据的存取。
     */
    void createFringeDevice();

}
