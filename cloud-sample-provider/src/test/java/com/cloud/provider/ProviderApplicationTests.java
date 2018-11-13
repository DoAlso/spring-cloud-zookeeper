package com.cloud.provider;

import com.cloud.provider.bean.FaceCreateParam;
import com.cloud.provider.bean.FaceSetInfo;
import com.cloud.provider.service.FaceDataBaseService;
import com.cloud.provider.service.FaceService;
import com.cloud.provider.service.FringeNodeService;
import com.cloud.provider.utils.FileTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ProviderApplicationTests
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/9 14:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {

    @Autowired
    private FaceDataBaseService faceDataBaseService;

    @Autowired
    private FaceService faceService;

    @Autowired
    private FringeNodeService fringeNodeService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFaceDataBaseCreate() throws Exception{
        FaceSetInfo setInfo = new FaceSetInfo();
        setInfo.setProject_id(1L);
        setInfo.setFace_set_name("hoolink");
        setInfo.setFace_set_capacity(100000);
        Map<String,Object> externalFields = new HashMap<>();
        externalFields.put("project_id","1");
        externalFields.put("project_name","晶日照明");
        setInfo.setExternal_fields(externalFields);
        String result = faceDataBaseService.createFaceDataBase(setInfo);
        assertThat(result).isEqualTo("SUCCESS");
    }

    @Test
    public void testFaceDataBaseDelete() throws Exception {
        boolean result = faceDataBaseService.deleteFaceDataBaseById(1L);
        assertThat(result).isEqualTo(true);
    }


    @Test
    public void testCreateFace() throws Exception {
        String base64 = FileTools.FileToBase64("E:\\Picture\\胡亚曦.jpg");
        FaceCreateParam createParam = new FaceCreateParam();
        createParam.setFace_set_name("hoolink");
        createParam.setImage_base64(base64);
        Map<String,Object> external_fields = new HashMap<>();
        external_fields.put("project_id",1);
        createParam.setExternal_fields(external_fields);
        boolean result = faceService.createFace(createParam);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testCreateFringeDevice() throws Exception {
        fringeNodeService.createFringeDevice();
    }

}
