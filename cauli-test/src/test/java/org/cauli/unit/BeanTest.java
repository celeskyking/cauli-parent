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
@Filter(runLevel = 3,feature = "test1",release = "3")
@ThreadRunner(threads = 3)
public class BeanTest {

    private Logger logger = LoggerFactory.getLogger(BeanTest.class);

    @Test
    @Tag(release = "0",name = "user",level = 2,feature = "test1")
    //@Ignore
    public void userTest() throws Exception {
        throw  new Exception("ERROR");
    }

    @Test
    @Tag(release = "3",name = "param",level = 2,feature = "test1")
    @Param(value = "test1.txt")
    @Dependency({"user"})
    public void paramTest(@Named("地点")String name,@Named("食物")String food) throws InterruptedException {
        logger.info("name:{}----- food:{} ",name,food);
    }


    @Test
    @Tag(release = "1",name="test",level = 1,feature = "test")
    //@Dependency("param")
    public void test(){
        logger.info("2");
    }


    @Test
    @Tag(release = "1" ,name = "test1",level = 0,feature = "test")
    public void test1(){
        logger.info("1");
    }


}
