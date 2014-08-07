package org.cauli.instrument;



import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cauli.algorithm.Tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tianqing.wang
 */
public class FieldTree implements Tree<FieldNode> {
    private FieldNode root;
    private List<FieldNode> leafNodes;
    //private Logger logger= LoggerFactory.getLogger(FieldTree.class);
    public FieldTree(Field field){
        this.root=new FieldNode(null,field);
        this.root.setDeep(1);
        this.leafNodes=new ArrayList<FieldNode>();
        iteratorTree(this.root);
    }

    private void iteratorTree(FieldNode fieldNode){
        if(fieldNode!=null){
            if(!fieldNode.hasChildren()){
                this.leafNodes.add(fieldNode);
            }else{
                for(FieldNode index : fieldNode.getChildren() ){
                    iteratorTree(index);
                }
            }
        }
    }

    public List<FieldNode> getLeafNodes() {
        return leafNodes;
    }

    public void setLeafNodes(List<FieldNode> leafNodes) {
        this.leafNodes = leafNodes;
    }

    public FieldNode getRoot() {
        return root;
    }

    public void setRoot(FieldNode root) {
        this.root = root;
    }

    public List<String> getLeafChains(){
        List<String> list = Lists.newArrayList();
        for(FieldNode fieldNode:getLeafNodes()){
            list.add(fieldNode.getChainPath());
        }
        return  list;
    }

    public Map<String,Class<?>> getLeafChainInfos(){
        Map<String,Class<?>> map= Maps.newHashMap();
        for(FieldNode fieldNode:getLeafNodes()){
            map.put(fieldNode.getChainPath(), fieldNode.getField().getType());
        }
        return map;
    }

}
