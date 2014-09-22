package org.cauli.mock.data;

/**
 * @auther sky
 */
public class DataProviderBuilder {

    public static volatile DataProviderBuilder builder;

    private IDataProvider provider;

    private DataProviderBuilder(){

    }


    public static DataProviderBuilder getInstance(){
        if(builder==null){
            builder=new DataProviderBuilder();
        }
        return builder;
    }


    public void configDataProvider(IDataProvider provider){
        this.provider=provider;
    }

    public IDataProvider getDataProvider(){
        return this.provider;
    }

}
