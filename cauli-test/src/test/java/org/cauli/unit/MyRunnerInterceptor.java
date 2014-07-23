package org.cauli.unit;

import com.google.common.collect.Maps;
import org.cauli.junit.RunnerInterceptor;
import org.cauli.junit.TestPlan;
import org.cauli.template.ValueTransfer;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/20
 */
public class MyRunnerInterceptor implements RunnerInterceptor{
    @Override
    public void beforeRunnerStart(TestPlan testPlan) {
        Map<String,Object> map = Maps.newHashMap();
        User userDto = new User();
        Brother brother = new Brother();
        brother.setAmount("200");
        userDto.setBrother(brother);
        map.put("user",userDto);
        testPlan.setTemplateSources(map);
    }
}
