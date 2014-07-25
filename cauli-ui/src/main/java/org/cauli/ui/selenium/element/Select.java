package org.cauli.ui.selenium.element;

import org.cauli.ui.selenium.browser.IBrowser;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Select extends CauliElement {
    private Logger logger = LoggerFactory.getLogger(Select.class);
    public org.openqa.selenium.support.ui.Select select;

    public Select(IBrowser browser) {
        super(browser);
    }

    public Select(IBrowser browser, String location) {
        super(browser, location);
        this.select=new org.openqa.selenium.support.ui.Select(getElement());
    }


    public void setSelectElement(WebElement element){
        setElement(element);
        this.select=new org.openqa.selenium.support.ui.Select(getElement());
    }


    public Select(IBrowser browser,CauliElement cauliElement){
        super(browser,cauliElement.getLocate());
    }


    public void setSelect(org.openqa.selenium.support.ui.Select select) {
        this.select = select;
    }

    /**根据comobobox中的索引值来进行选择，索引值从0开始的
     * @param index 要选择的索引号
     * */
    public void selectByIndex(int index){
        if(isExist()){
            getSelect().selectByIndex(index);
            logger.info("["+this.getId()+"]选择第"+index+"选项成功！");
        }else{
            logger.error("元素不存在，通过索引值"+index+"查找失败！");
            throw new NoSuchElementException(this.getId()+"]通过索引进行选择的时候失败了，这个元素不存在，没有被找到！");
        }
    }

    /**
     * 通过comobox中的内容进行选择，value属性值
     * @param value 要选择项的内容
     * */
    public void selectByValue(String value){
        if(isExist()){
            getSelect().selectByValue(value);
            logger.info("["+getId()+"]选择"+value+"所在项成功！");
        }else{
            logger.error("元素不存在，通过值"+value+"查找失败！");
            throw new NoSuchElementException("["+this.getId()+"]通过内容查找元素的时候失败了！这个元素不存在，没有被找到！");
        }

    }
    /**通过comobobox标签之间的内容来进行选择
     * @param text 要选择的项的内容值
     * */
    public void selectByVisiableText(String text){
        if(isExist()){
            select.selectByVisibleText(text);
            logger.info(this.getId()+"]选择值为"+text+"所在项成功！");
        }else{
            logger.error("元素不存在，通过内容值"+text+"查找失败！");
            throw new NoSuchElementException("["+this.getId()+"]通过标签之间的内容来进行查找的时候失败了，这个元素没有被找到！");
        }

    }
    /**获得当前选中项的索引值*/
    public int getSelectedIndex(){
        int selectedIndex=-1;
        if(isExist()){
            List<WebElement> allItems = new ArrayList<WebElement>();
            WebElement selectedItem=getSelect().getFirstSelectedOption();
            for(int i=0;i<allItems.size();i++){
                if(allItems.get(i).equals(selectedItem)){
                    selectedIndex=i;
                }
            }
            logger.info("["+this.getId()+"]获得选中项的索引值成功！");
            return selectedIndex;
        }else{
            logger.error("元素不存在，获得选中项的索引值失败！");
            throw new NoSuchElementException("["+this.getId()+"]获取当前项的索引值的时候出现了错误！可能的原因是这个元素不存在");
        }

    }
    /**获得当前选中项的内容*/
    public String getSelectedValue(){
        String value=null;
        if(isExist()){
            value=getSelect().getFirstSelectedOption().getText();
            logger.info("["+this.getId()+"]获得选中项的索引值成功！");
            return value;
        }else{
            logger.error("元素不存在，获得当前选中项内容失败！");
            throw new NoSuchElementException("["+this.getId()+"]获取当前项的内容的时候出现了错误！可能的原因是这个元素定位错误没有找到！");
        }
    }
    /**如果选择了多项，那么使用此方法可以获得多个选项*/
    public String[] getSelectedValues(){
        String[] values;
        if(isExist()){
            List<WebElement> selectedItems =getSelect().getAllSelectedOptions();
            values=new String[selectedItems.size()];
            for(int i=0;i<selectedItems.size();i++){
                values[i]=selectedItems.get(i).getText();
            }
            logger.info("["+this.getId()+"]获得全部选中项内容成功");
            return values;
        }else{
            logger.error("元素没有找到，获得内容失败！");
            throw new NoSuchElementException("["+this.getId()+"]获取多个元素的时候失败了！可能的原因是这个元素不存在，没有被找到！");
        }
    }
    /**如果选择了多项，那么使用此方法可以获得多个选项的索引值*/
    public int[] getSelectedIndexs(){
        int[] indexs = null;
        if(isExist()){
            List<WebElement> selectedItems =getSelect().getAllSelectedOptions();
            List<WebElement> allItems = getSelect().getOptions();
            indexs=new int[selectedItems.size()];
            for(int i=0;i<selectedItems.size();i++){
                for(int j=0;j<selectedItems.size();j++){
                    if(selectedItems.get(i).equals(allItems.get(j))){
                        indexs[i]=j;break;
                    }
                }
            }
            logger.info("["+this.getId()+"]获得全部选中项内容成功");
            return indexs;
        }else{
            logger.error("元素没有找到，获得索引值失败！");
            throw new NoSuchElementException("["+this.getId()+"]获取多个索引值的时候失败！可能的原因是这个元素没有找到！");
        }
    }
    /**获得控件里面的大小*/
    public int getComoboBoxSize(){
        int size=-1;
        if(isExist()){
            size= getSelect().getOptions().size();
            logger.info("["+this.getId()+"]得到comobox的大小为-->"+size);
            return size;
        }else{
            logger.error("元素不存在，返回comobox的大小失败！");
            throw new NoSuchElementException("["+this.getId()+"]获取控件大小的时候出现了错误！可能的原因是这个元素不存在");
        }
    }
    /**通过随机数来获得select的option的项*/
    public void selectByRandomIndex(){
        if(isExist()){
            Random r=new Random();
            int max=getSelect().getOptions().size();
            int random=r.nextInt(max);
            getSelect().selectByIndex(random);
            logger.info("["+this.getId()+"]选择了第"+getSelectedIndex()+"项，并且值为"+getSelectedValue());
        }else{
            logger.error("["+this.getId()+"]获取控件大小的时候出现了错误！可能的原因是这个元素不存在！");
            throw new NoSuchElementException("["+this.getId()+"]获取控件大小的时候出现了错误！可能的原因是这个元素不存在！");
            //logger.error("元素查找失败，没有找到相关的随即项！");
        }
    }

    public org.openqa.selenium.support.ui.Select getSelect() {
        return select;
    }
}
