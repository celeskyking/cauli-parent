package org.cauli.junit;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by celeskyking on 14-3-2
 */
public class TXTGenerator extends FileGenerator {
    private List<String> headers;
    public TXTGenerator(File txt,String type){
        super(txt,type);
        this.headers=Lists.newArrayList();
    }
    public TXTGenerator(File file){
        super(file);
        this.headers=Lists.newArrayList();
    }

    public List<RowParameter> generator() {
        List<RowParameter> parameters = Lists.newArrayList();
        if("col".equals(getType())){
            parameters = transferColToRow(getAllRows());
            RowParameter headerRow = parameters.get(0);
            for(String headerValue:headerRow.getParams()){
                this.headers.add(headerValue);
            }
            parameters.remove(0);
            return parameters;
        }else if("row".equals(getType())){
            parameters=getAllRows();
            RowParameter headerRow = parameters.get(0);
            for(String headerValue:headerRow.getParams()){
                this.headers.add(headerValue);
            }
            parameters.remove(0);
            return parameters;
        }
        return parameters;
    }

    @Override
    public List<String> getHeaders() {
        return this.headers;
    }

    private List<RowParameter> getAllRows(){
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(getFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件出现错误错误...");
        }
        List<RowParameter> parameters = Lists.newArrayList();

        for(String line:lines){
            String[] strings = StringUtils.split(line, "|");
            RowParameter rowParameter=new RowParameter();
            for(String str:strings){
                rowParameter.addParam(org.apache.commons.lang3.StringUtils.trim(str));
            }
            parameters.add(rowParameter);
        }
        return parameters;
    }

    private List<RowParameter> transferColToRow(List<RowParameter>rolParams){
        List<RowParameter> colParam = Lists.newArrayList();
        for(int i=0;i<rolParams.get(0).getParams().size();i++){
            RowParameter tempParam = new RowParameter();
            for(RowParameter parameter:rolParams){
                tempParam.addParam(parameter.getParams().get(i));
            }
            colParam.add(tempParam);
        }
        return colParam;
    }


}
