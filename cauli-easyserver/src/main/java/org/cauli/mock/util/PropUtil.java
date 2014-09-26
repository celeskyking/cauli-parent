package org.cauli.mock.util;

import jodd.props.Props;
import jodd.props.PropsEntries;
import jodd.props.PropsEntry;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.instrument.ResourceUtil;
import org.cauli.common.keyvalue.ValueHandler;
import org.cauli.mock.context.Context;
import org.cauli.common.keyvalue.KeyValueStore;
import org.cauli.common.keyvalue.KeyValueStores;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @auther sky
 */
public class PropUtil {

    public static KeyValueStores loadFile(Context context,File file,String section,Comparator comparator) throws Exception {
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
            String value = TemplateParseUtil.getInstance().toString(context.getValues(),entry.getValue());
            stores.add(new KeyValueStore(StringUtils.substringAfter(entry.getKey(),"."),value));
        }
        return stores;

    }

    public static KeyValueStores loadFile(Context context,File file,String section,Comparator comparator,ValueHandler valueHandler) throws Exception {
        Props props = new Props();
        props.load(file);
        PropsEntries entries = props.entries();
        PropsEntries sectionEntries = entries.section(section);
        Iterator<PropsEntry> iterator =sectionEntries.iterator();
        KeyValueStores stores;
        if(comparator!=null){
            stores = new KeyValueStores(comparator,valueHandler);
        }else{
            stores = new KeyValueStores(valueHandler);
        }

        while(iterator.hasNext()){
            PropsEntry entry = iterator.next();
            String value = TemplateParseUtil.getInstance().toString(context.getValues(),entry.getValue());
            stores.add(new KeyValueStore(StringUtils.substringAfter(entry.getKey(),"."),value));
        }
        return stores;

    }

    public static KeyValueStores loadFile(Context context,File file,String section,ValueHandler valueHandler) throws Exception {
        return loadFile(context,file,section,null,valueHandler);
    }

    public static KeyValueStores loadFileByClasspath(Context context,String fileName,String section,Comparator comparator,ValueHandler valueHandler) throws Exception {
        File file = ResourceUtil.getFileFromClassPath(fileName);
        return loadFile(context,file,section,comparator,valueHandler);
    }

    public static KeyValueStores loadFileByClasspath(Context context,String fileName,String section,ValueHandler valueHandler) throws Exception {
        File file = ResourceUtil.getFileFromClassPath(fileName);
        return loadFile(context,file,section,null,valueHandler);
    }
}
