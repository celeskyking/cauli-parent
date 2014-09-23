package org.cauli.mock.context;

import com.google.common.collect.Maps;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.cauli.common.instrument.ResourceUtil;
import org.cauli.mock.ValueType;
import org.cauli.mock.annotation.Register;
import org.cauli.mock.annotation.Value;
import org.cauli.mock.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/8/7
 */
public class TemplateValueInit {


    private Map<String,TemplateMethodModelEx> map= Maps.newHashMap();

    private Logger logger = LoggerFactory.getLogger(TemplateValueInit.class);

    private Map<String,Object> objectMap = Maps.newHashMap();

    private TemplateValueInit() throws Exception {
        init();
    }

    public volatile static TemplateValueInit valueInit;

    public static TemplateValueInit getInstance() throws Exception {
        if(valueInit==null){
            synchronized (TemplateValueInit.class){
                if(valueInit==null){
                    valueInit=new TemplateValueInit();
                }
            }
        }
        return valueInit;
    }

    public void init() throws Exception {
        Set<Class<?>> classSet = ResourceUtil.getClassContainsAnnotation(Register.class);
        for(Class<?> clazz:classSet){
            parseMethods(clazz);
        }
    }

    //-------------------------help methods------------------------//

    private void parseMethods(Class<?> clazz) throws Exception {
        volidateClassInfo(clazz);
        final Object obj = clazz.newInstance();
        for(final Method method:clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(Value.class)&&method.getAnnotation(Value.class).type()== ValueType.METHOD){
                volidateMethodInfo(method);
                logger.info("扫描到全局模板方法:{}",method.getAnnotation(Value.class).value());
                map.put(method.getAnnotation(Value.class).value(), new TemplateMethodModelEx() {
                    @Override
                    public Object exec(List arguments) throws TemplateModelException {
                        try {
                            if(CommonUtil.getMethodParameterCount(method)==0){
                                return method.invoke(obj);
                            }else{
                                return method.invoke(obj,arguments);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else if(method.isAnnotationPresent(Value.class)&&method.getAnnotation(Value.class).type()== ValueType.OBJECT){
                volidateObjectMethodInfo(method);
                logger.info("扫描到全局模板变量:{}",method.getAnnotation(Value.class).value());
                objectMap.put(method.getAnnotation(Value.class).value(),method.invoke(obj));
            }
        }
    }

    private void volidateClassInfo(Class<?> clazz){
        if(clazz.getDeclaredConstructors().length>1||CommonUtil.getConstructorParameterCount(clazz.getDeclaredConstructors()[0])>0){
            throw new RuntimeException(clazz.getName()+"只能够包含默认的构造方法");
        }
    }

    public Map<String,TemplateMethodModelEx> getTemplateConstant(){
        return map;
    }

    public Map<String,Object> getTemplateObjects(){
        return objectMap;
    }

    private void volidateMethodInfo(Method method){
        if((CommonUtil.getMethodParameterCount(method)==1&&method.getParameterTypes()[0]!=List.class)||CommonUtil.getMethodParameterCount(method)>1){
            throw new RuntimeException(method.getName()+"必须有List类型的参数或者不包含参数");
        }
    }

    private void volidateObjectMethodInfo(Method method){
        if(CommonUtil.getMethodParameterCount(method)!=0){
            throw new RuntimeException(method.getName()+"必须不含有参数");
        }
    }

}
