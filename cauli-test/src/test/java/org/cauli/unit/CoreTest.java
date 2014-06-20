package org.cauli.unit;


import org.cauli.junit.CauliRunner;
import org.cauli.junit.anno.Interceptor;
import org.cauli.junit.anno.Named;
import org.cauli.junit.anno.Param;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/13
 */
@RunWith(CauliRunner.class)
@Interceptor(MyRunnerInterceptor.class)
public class CoreTest {

    @Test
    @Param(value = "test1.txt")
    @Ignore
    public void testMap(@Named("params")Map<String,String> params){
        System.out.println(params.toString());
    }



    @Test
    @Param("test1.txt")
    public void testTemplate(@Named("user")Map<String,String> name){
        System.out.println(name);
    }


}
