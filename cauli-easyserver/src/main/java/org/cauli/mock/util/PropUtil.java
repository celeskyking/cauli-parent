package org.cauli.mock.util;

import jodd.props.Props;
import jodd.props.PropsEntries;
import jodd.props.PropsEntry;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.instrument.ResourceUtil;
import org.cauli.mock.entity.KeyValueStore;
import org.cauli.mock.entity.KeyValueStores;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @auther sky
 */
public class PropUtil {

    public static KeyValueStores loadFile(File file,String section,Comparator comparator) throws IOException {
        Props props = new Props();
        props.load(file);
        PropsEntries entries = props.entries();
        PropsEntries sectionEntries = entries.section(section);
        Iterator<PropsEntry> iterator =sectionEntries.iterator();
        KeyValueStores stores;
        if(comparator!=null){
            stores = new KeyValueStores(comparator);
        }else{
            stores = new KeyValueStores();
        }

        while(iterator.hasNext()){
            PropsEntry entry = iterator.next();
            stores.add(new KeyValueStore(StringUtils.substringAfter(entry.getKey(),"."),entry.getValue()));
        }
        return stores;

    }

    public static KeyValueStores loadFile(File file,String section) throws IOException {
        return loadFile(file,section,null);
    }

    public static KeyValueStores loadFileByClasspath(String fileName,String section,Comparator comparator) throws IOException {
        File file = ResourceUtil.getFileFromClassPath(fileName);
        return loadFile(file,section,comparator);
    }

    public static KeyValueStores loadFileByClasspath(String fileName,String section) throws IOException {
        File file = ResourceUtil.getFileFromClassPath(fileName);
        return loadFile(file,section,null);
    }
}
