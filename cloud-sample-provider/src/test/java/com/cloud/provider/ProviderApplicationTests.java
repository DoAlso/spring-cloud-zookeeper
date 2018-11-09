package com.cloud.provider;

import com.cloud.provider.bean.FaceSetInfo;
import com.cloud.provider.service.FaceDataBaseService;
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

}
