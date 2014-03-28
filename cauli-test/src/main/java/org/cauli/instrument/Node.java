package org.cauli.instrument;

import java.util.List;

/**
 * Created by tianqing.wang on 13-12-27
 */
public interface Node<T> {

    public T getParent();

    public boolean isRoot();

    public boolean isLeaf();
    /**获取此节点到根节点的路径*/
    public Object getChainPath();
    /**获取深度*/
    public int getDeep();
    /**获取此元素下一深度的所有子节点*/
    public List<T> getChildren();

    public boolean hasChildren();
}
