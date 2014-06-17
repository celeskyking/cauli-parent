package org.cauli.junit;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cauli.exception.DuplicateParameterNameException;
import org.cauli.pairwise.algorithm.FullCombinationAlgorithm;
import org.cauli.pairwise.core.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by celeskyking on 14-3-2
 */
public class TXTGenerator extends FileGenerator {

    public TXTGenerator(File txt,String type){
        super(txt,type);

    }
    public TXTGenerator(File file){
        super(file);

    }


    protected List<Parameter> getAllPairwiseRows() throws IOException {
        List<String> rows= FileUtils.readLines(getFile());
        List<Parameter> parameters = Lists.newArrayList();
        for(int i=0;i<rows.size();i++){
            String name = StringUtils.substringBefore(rows.get(i),":");
            String values[]=StringUtils.split(StringUtils.substringAfter(rows.get(i),":"),"|");
            getHeaders().add(name);
            parameters.add(new Parameter(Long.valueOf(i),name, Arrays.asList(values)));
        }
        return parameters;
    }



    protected List<RowParameter> getAllRows() throws IOException {
        List<String> lines ;
        lines = FileUtils.readLines(getFile());
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




}
