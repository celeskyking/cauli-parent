package org.cauli.junit;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.cauli.pairwise.algorithm.FullCombinationAlgorithm;
import org.cauli.pairwise.core.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import java.util.Arrays;
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


    protected List<Parameter> getAllPairwiseRows() throws IOException {
        int numberOfRows = this.sheet.getPhysicalNumberOfRows();
        List<Parameter> parameters= Lists.newArrayList();
        for(int i=0;i<numberOfRows;i++){
            Row row = this.sheet.getRow(i);
            int cellNum = row.getPhysicalNumberOfCells();
            List<String> list =Lists.newArrayList();
            for(int j=0;j<cellNum;j++){
                if(i==0){
                    Cell cell = row.getCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    this.headers.add(StringUtils.trim(row.getCell(j).getStringCellValue()));
                }else{
                    Cell cell = row.getCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    list.add(StringUtils.trim(row.getCell(j).getStringCellValue()));
                }

            }
            parameters.add(new Parameter(Long.valueOf(i),this.headers.get(i),list));
        }
        return parameters;
    }


    protected List<RowParameter> getAllRows(){
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



}
