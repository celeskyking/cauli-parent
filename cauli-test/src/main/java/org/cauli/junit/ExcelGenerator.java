package org.cauli.junit;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.lang.reflect.Method;

import java.util.List;

/**
 * Created by celeskyking on 14-3-2
 */
public class ExcelGenerator extends FileGenerator{
    private List<String> headers;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelGenerator(File excel,String type){
        super(excel,type);
        if(!excel.exists()){
            throw new RuntimeException("没有找到excel文件");
        }else {
            try {
                this.workbook = WorkbookFactory.create(excel);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("初始化excel文件的时候出现了错误");
            }
            this.sheet=this.workbook.getSheetAt(0);

        }
        this.headers=Lists.newArrayList();
    }

    public ExcelGenerator(File excel,Method method) {
        super(excel);
        if(!excel.exists()){
            throw new RuntimeException("没有找到excel文件");
        }else {
            try {
                this.workbook = WorkbookFactory.create(excel);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("初始化excel文件的时候出现了错误");
            }
            this.sheet=this.workbook.getSheet(method.getName());
        }
        this.headers=Lists.newArrayList();
    }

    public List<RowParameter> generator()    {
        List<RowParameter> rowParameters;
        if("col".equals(getType())){
            List<RowParameter> colParams = getAllRows();
            rowParameters = transferColToRow(colParams);
            RowParameter headerRow = rowParameters.get(0);
            for(String headerValue:headerRow.getParams()){
                this.headers.add(headerValue);
            }
            rowParameters.remove(0);
            return rowParameters;

        }else if("row".equals(getType())){
            rowParameters = getAllRows();
            RowParameter headerRow = rowParameters.get(0);
            for(String headerValue:headerRow.getParams()){
                this.headers.add(headerValue);
            }
            rowParameters.remove(0);
            return rowParameters;
        }
        return null;

    }

    @Override
    public List<String> getHeaders() {
        return this.headers;
    }

    private List<RowParameter> getAllRows(){
        int numberOfRows = this.sheet.getPhysicalNumberOfRows();
        List<RowParameter> rowParameters= Lists.newArrayList();
        for(int i=0;i<numberOfRows;i++){
            Row row = this.sheet.getRow(i);
            int cellNum = row.getPhysicalNumberOfCells();
            RowParameter rowParameter=new RowParameter();
            for(int j=0;j<cellNum;j++){
                Cell cell = row.getCell(j);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                rowParameter.addParam(StringUtils.trim(row.getCell(j).getStringCellValue()));
            }
            rowParameters.add(rowParameter);
        }
        return rowParameters;
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
