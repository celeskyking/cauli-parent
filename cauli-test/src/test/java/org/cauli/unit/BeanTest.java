package org.cauli.unit;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.JUnitBaseRunner;
import org.cauli.junit.anno.Filter;
import org.cauli.junit.anno.Param;
import org.cauli.junit.anno.Tag;
import org.cauli.junit.anno.ThreadRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by celeskyking on 2014/3/30
 */
@RunWith(JUnitBaseRunner.class)
@Ignore
@Filter
@ThreadRunner(threads = 2)
public class BeanTest {

    @Test
    @Tag(release = "test_card")
    public void userTest() throws InvocationTargetException, IllegalAccessException, InterruptedException {

    }

    @Test
    @Tag(release = "card")
    @Param("test.txt")
    //@Source("test.xls")
    public void paramTest(String name) throws InterruptedException {
        Thread.sleep(4000);
        System.out.println(name);
    }


}
