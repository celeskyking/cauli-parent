package org.cauli.unit;

import org.cauli.junit.CauliRunner;
import org.cauli.junit.anno.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by celeskyking on 2014/3/30
 */
@RunWith(CauliRunner.class)
public class BeanTest {

    @Test
    @Tag(release = "test_card",name = "user")
    //@Dependency("test")
    @Ignore
    public void userTest() {
        System.out.println("依赖我的");
    }

    @Test
    @Tag(release = "card",name = "param",level = 0)
    @Param("test.txt")
    @Ignore
    public void paramTest(@Named("name")String name,@Bean("user")User user) throws InterruptedException {
        //assertThat("校验参数化", name + "___" + user.getBrother().getAmount(), containsString("北京___0.01"));
        System.out.println(name);
    }


    @Test
    @Tag(release = "1",name="test",level = 2)
    @Dependency("param")
    public void test(){
        System.out.println("just for test");
    }


}
