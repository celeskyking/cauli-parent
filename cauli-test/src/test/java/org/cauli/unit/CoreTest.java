package org.cauli.unit;


import org.cauli.junit.runner.CauliRunner;
import org.cauli.junit.anno.Interceptor;
import org.cauli.junit.anno.Named;
import org.cauli.junit.anno.Param;
import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/13
 */
@RunWith(CauliRunner.class)
@Interceptor(MyRunnerInterceptor.class)
public class CoreTest {

    //@Test
    //@Param(value = "test1.txt")
    public void testMap() throws JSONException {
        String result = "{id:1,name:\"Juergen\"}";
        JSONAssert.assertEquals("{id:1}", result, false); // Pass
        //JSONAssert.assertEquals("{id:1}", result, true); // Fail
    }



    @Test
    @Param("test1.txt")
    public void testTemplate(@Named String name,@Named String result){
        System.out.println(name+result);
    }


}
