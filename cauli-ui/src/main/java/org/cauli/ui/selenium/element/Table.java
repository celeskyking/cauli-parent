package org.cauli.ui.selenium.element;

import org.cauli.ui.selenium.browser.IBrowser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class Table extends CauliElement {
    private Logger logger = LoggerFactory.getLogger(Table.class);

    public Table(IBrowser browser) {
        super(browser);
    }

    public Table(IBrowser browser, String location) {
        super(browser, location);
    }

    public Table(IBrowser browser,CauliElement cauliElement){
        super(browser,cauliElement.getLocate());
    }

    /**
	 *获得table的第row行，col列的值，这个方法只能返回td的值，对于标题th标签是不支持的，可以用getTableTitle的方法，</br>
	 *都是在同一个table中，按正常的行数数就可以。 
	 * 行号和列号都从0开始
	 * @param row 行号
	 * @param col 列号
	 * @return 此table的指定行，指定列的值
	 * */
	public String getTableContent(int row,int col){
		String content=null;
		if(isExist()){
			try{
				content=getElement().findElement(By.xpath(".//tr[" + (row) + "]/td[" + (col) + "]")).getText();
				logger.info("["+this.getId()+"]第"+(row)+"行，第"+(col)+"列的元素获取成功！");
			}catch(Exception e){
				logger.info("[["+this.getId()+"]第"+(row)+"行，第"+(col)+"列的元素获取失败！找不到这个table元素！",e);
			}
			return content;
		}else{
			logger.error("第"+(row)+"行，第"+(col)+"列的元素获取成功！");
			throw new RuntimeException("["+this.getId()+"]获得table内的数据的时候出现错误，可能的原因是元素没有被找到！");
		}
		
	}


    public IElement cell(int row ,int col){
        if(isExist()){
            CauliElement cauliElement = new CauliElement(getBrowser());
            cauliElement.setElement(getElement().findElement(By.xpath(".//tr[" + row + "]/td[" + col + "]")));
            return cauliElement;
        }else {
            throw new RuntimeException("["+this.getId()+"]获得table内的数据的时候出现错误，可能的原因是元素没有被找到！");
        }
    }
	/**这个方法返回的是table的标题内容
	 * @param row 行号
	 * @param col 列号
	 * @return 此table的指定行，指定列的值
	 * */
	public String getTableTitle(int row,int col){
		String content=null;
		if(isExist()){
			try{
				content=getElement().findElement(By.xpath(".//tr[" + (row) + "]/th{" + (col) + "]")).getText();
				logger.info("["+this.getId()+"]第"+(row+1)+"行，第"+(col+1)+"列的元素获取成功！");
			}catch(Exception e){
				logger.info("["+this.getId()+"]第"+(row+1)+"行，第"+(col+1)+"列的元素获取失败！找不到这个table元素！");
			}
			return content;
		}else{
			logger.error("第"+(row+1)+"行，第"+(col+1)+"列的元素获取成功！");
			throw new RuntimeException("["+this.getId()+"]获得table内的数据的时候出现错误，可能的原因是元素没有被找到！");
		}
		
	}
	
	/**
	 * 获得此table第col列所有行的值，列号从0开始
	 * @param col 列号
	 * @return 字符串list，col列的所有行的信息
	 * */
	public List<String> getTableContentByColumn(int col){
		if(isExist()){
			try{
				List<String> contents =new ArrayList<String>();
				List<WebElement> cells=getElement().findElements(By.xpath(".//td{" + (col + 1) + "]"));
				for(WebElement cell : cells){
					contents.add(cell.getText());
				}
				logger.info("["+this.getId()+"]第"+col+"列的信息获得成功！");
				return contents;
			}catch(Exception e){
				logger.error("第"+col+"列的信息获得失败！！");
			}
		}else{
			logger.error(">>["+this.getId()+"]第"+col+"列的信息获得失败！！元素没有找到！");
			throw new RuntimeException("["+this.getId()+"]查找这个元素的时候出现了错误，可能这个元素不存在，没有找到元素！");
		}
		
		return null;
	}
	/**
	 * 获得此table的所有行和所有列的值
	 * @return 获得所有行和所有列的值，如果table是空的，那么返回null
	 * */
	public List<List<String>> getAllTableContext(){
		List<List<String>> contents = new ArrayList<List<String>>();
		int rowCount=0;
		if(isExist()){
			try{
				while(true){
					if(getElement().findElements(By.xpath(".//tr[" + (rowCount + 1) + "]")).size()==0){
						break;
					}
					contents.add(getTableContentByRow(rowCount));
					rowCount++;
					logger.info("["+this.getId()+"]第"+rowCount+"行的所有信息获得成功！");
				}
			}catch(Exception e){
				logger.info("第"+rowCount+"行的所有信息获得失败！没有找到这个table内的要查找的元素！");
			}
			return contents;
		}else{
			logger.info("第"+rowCount+"行的所有信息获得失败！没有找到这个table元素！");
			throw new RuntimeException("["+this.getId()+"]查找table的所有元素的时候出现错误！可能这个元素不存在！");
		}
		
	}
	/**
	 * 获得table第row行所有的值，行号从0开始
	 * @param row 行号
	 * @return 字符串list，row列所有的信息
	 * */
	private List<String> getTableContentByRow(int row) {
		List<String> contents=new ArrayList<String>();
		if(isExist()){
			try{
				WebElement rowCell = getElement().findElement(By.xpath(".//tr[" + (row + 1) + "]"));
				List<WebElement> cells  = rowCell.findElements(By.xpath(".//td"));
				for(WebElement cell : cells){
					contents.add(cell.getText());
				}
				logger.info("["+this.getId()+"]第"+row+"行的信息获得成功！");
			}catch(Exception e){
				logger.info("第"+row+"行的信息获得失败！找不到table内的要查找的的元素！");
			}
			return contents;
		}else{
			logger.info("第"+row+"行的信息获得失败！找不到这个table的元素！");
			throw new RuntimeException("["+this.getId()+"]查找table的值时出现了错误，可能的原因是这个table元素定位错误！没有找到这个元素");
		}
	
	}
	
	
	
}
