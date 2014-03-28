package org.cauli.ui.selenium.element;

import org.jsoup.nodes.Element;

import java.util.Stack;

public class JSoupElement {
	private Element element;
	private Stack<String> stack;
	private boolean isDisappearId=false;
	public JSoupElement(Element element){
		this.element=element;
		this.stack=new Stack<String>();
	}
	
	private void colloct(){
		this.stack.clear();
		Element node;
		if(hasID(this.element)){
			this.stack.push(this.element.id());
			isDisappearId=true;
		}else{
			node=this.element;
			while(!hasID(node)){
				if(node.parent().children().size()==1){
					String tagname=node.tagName();
					if(tagname.contains(":")){
						tagname=tagname.substring(tagname.lastIndexOf(":")+1);
					}
					this.stack.push(tagname);
				}else{
					String tagname=node.tagName();
					if(tagname.contains(":")){
						tagname=tagname.substring(tagname.lastIndexOf(":")+1);
					}
					int index=node.elementSiblingIndex().intValue()+1;
					this.stack.push(tagname+"["+index+"]");
				}
				node=node.parent();
			}
			if(hasID(node)){
				this.stack.push(node.id());
				isDisappearId=true;
			}
		}
	}
	
	private boolean hasID(Element e){
		if(e.id()!=null&&!e.id().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	public String toXpath(){
		String xpath="";
		colloct();
		if(isDisappearId){
			int i=0;
			while(!this.stack.empty()){
				if(i==0){
					xpath=xpath+".//*[@id='"+this.stack.pop()+"']";
				}else{
					xpath=xpath+"/"+this.stack.pop();
				}
				i++;
			}
		}else{
			while(!this.stack.empty()){
				xpath=xpath+"/"+this.stack.pop();
			}
		}
		return xpath;
	}
	
	
//	public static void main(String[] args) throws InterruptedException {
//		WebDriver driver = new FirefoxDriver();
//		driver.get("http://www.hao123.com");
//		String source = driver.getPageSource();
//		Document doc = Jsoup.parse(source);
//		Element e=doc.select("img[src=http://s1.hao123img.com/index/images/search_logo/web.png]").first();
//		JSoupElement je = new JSoupElement(e);
//		System.out.println(je.toXpath());
//		driver.findElement(By.xpath(je.toXpath())).click();
//		Thread.sleep(3000);
//		driver.quit();			
//	}
}
