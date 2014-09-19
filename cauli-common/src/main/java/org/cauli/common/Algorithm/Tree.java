package org.cauli.common.Algorithm;


import java.util.List;

/**
 * Created by tianqing.wang on 13-12-27
 */
public interface Tree<T> {
    /**获取所有的叶节点*/
    public List<T> getLeafNodes();
    /**获取根节点*/
    public T getRoot();
    /**获取所有叶节点到根节点的路径*/
    public List<?> getLeafChains();
}
