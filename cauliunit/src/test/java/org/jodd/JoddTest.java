package org.jodd;

import jodd.paramo.MethodParameter;
import jodd.paramo.Paramo;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 2014/7/28
 */
public class JoddTest {


    public void hello(String user){
        System.out.println(user);
    }


    @Test
    public void testMethodParamo() throws NoSuchMethodException {
        Method method = JoddTest.class.getMethod("hello",String.class);
        MethodParameter[] parameters = Paramo.resolveParameters(method);
        for(MethodParameter parameter:parameters){
            System.out.println(parameter.getName());
        }
    }
}
