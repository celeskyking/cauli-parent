package org.cauli.unit;

import org.cauli.junit.CauliRunner;
import org.cauli.junit.anno.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by celeskyking on 2014/3/30
 */
@RunWith(CauliRunner.class)
@Filter(runLevel = 3)
@ThreadRunner(threads = 2)
public class BeanTest {

    private Logger logger = LoggerFactory.getLogger(BeanTest.class);

    @Test
    @Tag(release = "3",name = "user",level = 2,feature = "test")
    @Ignore
    public void userTest() throws InterruptedException {
        Thread.sleep(4000);
        logger.info("依赖我的");
    }

    @Test
    @Tag(release = "2",name = "param",level = 2,feature = "test")
    @Param("test.txt")
    @Ignore
    public void paramTest(@Named("name")String name,@Bean("user")User user) throws InterruptedException {
        //assertThat("校验参数化", name + "___" + user.getBrother().getAmount(), containsString("北京___0.01"));
        Thread.sleep(3000);
        logger.info(name);
    }


    @Test
    @Tag(release = "1",name="test",level = 2,feature = "test")
    @Dependency("param")
    public void test() throws InterruptedException {
        Thread.sleep(5000);
        logger.info("just for test");
    }


}
