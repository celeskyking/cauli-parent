package org.cauli.junit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cauli.pairwise.algorithm.FullCombinationAlgorithm;
import org.cauli.pairwise.core.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by celeskyking on 14-3-2
 */
public abstract class FileGenerator {

    private File file;
    private String type;
    private boolean isPairwise=false;

    private PairwiseFilter filter;

    private List<String> headers=Lists.newArrayList();
    /**
     * 增加一个参数来判断我们的参数化文件是使用横向模式，还是使用列向模式
     * @param type row为横向，col为列向
     * */
    public FileGenerator(File file,String type){
        setFile(file);
        this.type=type;
    };
    /**默认使用行模式*/
    public FileGenerator(File file){
        setFile(file);
        setType("row");
        isPairwise=false;
    }


    protected List<PairParameter> transferRowParameter(List<RowParameter> rowParameters){
        List<PairParameter> pairParameters= Lists.newArrayList();
        for(RowParameter rowParameter:rowParameters){
            PairParameter pairParameter = new PairParameter();
            for(int i=0;i<rowParameter.getParams().size();i++){
                ParameterValuePair parameterValuePair = new ParameterValuePair(this.headers.get(i),rowParameter.getParams().get(i));
                pairParameter.addParam(parameterValuePair);
            }
            pairParameters.add(pairParameter);
        }
        return pairParameters;
    }

    abstract List<RowParameter> getAllRows() throws IOException;

    public List<PairParameter> generator() throws IOException {
        List<RowParameter> parameters;
        if(isPairwise()){
            return parsePairwise(getAllPairwiseRows());
        }else{
            parameters = "col".equals(getType())?transferColToRow(getAllRows()):getAllRows();
            RowParameter headerRow = parameters.get(0);
            for(String headerValue:headerRow.getParams()){
                this.headers.add(headerValue);
            }
            parameters.remove(0);
            return transferRowParameter(parameters);
        }

    }

    protected List<RowParameter> transferColToRow(List<RowParameter>rolParams){
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

    protected List<PairParameter> parsePairwise(List<Parameter> parameters){
        List<PairParameter> pairParameters = Lists.newArrayList();

        Algorithm algorithm = new FullCombinationAlgorithm();
        CombinationList combinationList =algorithm.generate(new ParameterList(parameters),-1);
        for(Combination combination:combinationList.getCombinations()){
            List<ParameterValuePair> valuePairs=Lists.newArrayList();
            PairParameter pairParameter = new PairParameter();
            Map<String,String> conditions = Maps.newHashMap();
            for(ParameterValuePair valuePair:combination.getMap().values()){
                valuePairs.add(valuePair);
                conditions.put(valuePair.getParameterName(),valuePair.getParameterValue());
            }
            if(filter.isMatch(conditions)){
                pairParameter.addParams(valuePairs);
                pairParameters.add(pairParameter);
            }


        }
        return pairParameters;
    }

    abstract List<Parameter> getAllPairwiseRows() throws IOException;


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPairwise() {
        return isPairwise;
    }

    public void setPairwise(boolean isPairwise) {
        this.isPairwise = isPairwise;
    }

    public PairwiseFilter getFilter() {
        return filter;
    }

    public void setFilter(PairwiseFilter filter) {
        this.filter = filter;
    }

    public List<String> getHeaders(){
        return this.headers;
    }
}
