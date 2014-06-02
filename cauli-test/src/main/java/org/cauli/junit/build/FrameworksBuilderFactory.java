package org.cauli.junit.build;

/**
 * Created by celeskyking on 2014/6/1
 */
public class FrameworksBuilderFactory {

    private volatile static FrameworksBuilderFactory factory;

    public static FrameworksBuilderFactory getInstance(){
        if(factory==null){
            synchronized (FrameworksBuilderFactory.class){
                if(factory==null){
                    factory=new FrameworksBuilderFactory();
                }
            }
        }
        return factory;
    }

    private FrameworksBuilder frameworksBuilder;

    public FrameworksBuilder getFrameworkBuilder() {
        return frameworksBuilder;
    }

    public void setFrameworkBuilder(FrameworksBuilder frameworksBuilder) {
        this.frameworksBuilder = frameworksBuilder;
    }


    private FrameworksBuilderFactory(){
        this.frameworksBuilder=new DefaultFrameworksBuilder();
    }

}
