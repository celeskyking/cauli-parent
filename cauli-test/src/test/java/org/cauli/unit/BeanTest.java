package org.cauli.unit;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.JUnitBaseRunner;
import org.cauli.junit.anno.Param;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by celeskyking on 2014/3/30
 */
@RunWith(JUnitBaseRunner.class)
public class BeanTest {

    @Test
    public void userTest() throws InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = Maps.newHashMap();
        Brother brother = new Brother();
        brother.setAmount(new BigDecimal("0.01"));
        map.put("brother",brother);
        User user = new User();
        long start = System.currentTimeMillis();
        BeanUtils.copyProperties(map,user);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(JSON.toJSONString(user, true));
    }

    //@Test
    @Param("test.txt")
    public void paramTest(String name,int age){
        System.out.println("name:"+name+", age:"+age);
    }


}
