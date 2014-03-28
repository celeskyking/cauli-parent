package org.cauli.junit;

import java.io.File;
import java.util.List;

/**
 * Created by celeskyking on 14-3-2
 */
public abstract class FileGenerator {

    private File file;
    private String type;
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
    }

    abstract public List<RowParameter>  generator();

    abstract public List<String> getHeaders();

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
}
