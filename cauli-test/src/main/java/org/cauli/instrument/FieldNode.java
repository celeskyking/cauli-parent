package org.cauli.instrument;

import com.google.common.base.Joiner;
import org.cauli.algorithm.Node;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianqing.wang
 * 多叉树的遍历
 */
public class FieldNode implements Node<FieldNode> {
    private Field field;
    private List<FieldNode> children;
    private FieldNode parent;
    private int deep;
    private String name;

    public FieldNode(FieldNode parent,Field field){
        children = new ArrayList<FieldNode>();
        if(parent==null){
            this.deep=1;
        }else{
            this.parent=parent;
            this.deep=parent.getDeep()+1;
        }
        this.field=field;
        this.name=field.getName();
        if(hasChildren()){
            Field[] fields=this.field.getType().getDeclaredFields();
            for(Field f:fields){
                if(!FieldUtils.isFinal(f)){
                    children.add(new FieldNode(this,f));
                }
            }
        }

    }

    public Field getField() {
        return field;
    }

    public void setChildren(Field field) {
        this.field = field;
    }

    public List<FieldNode> getChildren() {
        return children;
    }

    public void setFields(List<FieldNode> fields) {
        this.children = fields;
    }


    public boolean hasChildren(){
        if(FieldUtils.isSimpleType(this.field)){
            return false;
        }else{
            return true;
        }
    }

    public FieldNode getParent() {
        return parent;
    }

    public void setParent(FieldNode parent) {
        this.parent = parent;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**返回从叶节点到根节点路径*/
    public String getChainPath(){

        FieldNode fieldNode=this;
        String[] strings =new String[fieldNode.getDeep()];
        for(int i=fieldNode.getDeep();i>=1;i--){
            strings[i-1]=fieldNode.getName();
            fieldNode=fieldNode.getParent();
        }
        return Joiner.on(".").join(strings);
    }

    public boolean isRoot(){
        if(getDeep()==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isLeaf(){
        if(hasChildren()){
            return false;
        }else{
            return true;
        }
    }
}
