package org.cauli.unit;

import org.cauli.junit.anno.*;
import org.cauli.junit.runner.CauliRunner;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by celeskyking on 2014/3/30
 */
@RunWith(CauliRunner.class)
@Filter(runLevel = 3)
@ThreadRunner(threads = 1)
public class BeanTest {


    @Test
    @Tag(name = "test1",level = 0)
    @Param("test1.txt")
    public void test1(@Named("result")int result){
        System.out.println(result);
    }


}
