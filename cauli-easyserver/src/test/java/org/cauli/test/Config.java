package org.cauli.test;

import org.cauli.mock.ValueType;
import org.cauli.mock.annotation.Register;
import org.cauli.mock.annotation.Value;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/9/23
 * */
@Register
public class Config {

    @Value(type = ValueType.METHOD,value = "hello")
    public String hello(){
        return "good ";
    }
}
