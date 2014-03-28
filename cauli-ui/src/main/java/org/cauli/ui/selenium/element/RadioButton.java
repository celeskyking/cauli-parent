package org.cauli.ui.selenium.element;


import org.cauli.ui.selenium.browser.IBrowser;

public class RadioButton extends CheckBox {


    public RadioButton(IBrowser browser, TempElement tempElement) {
        super(browser, tempElement);
    }

    public RadioButton(IBrowser browser) {
        super(browser);
    }

    public boolean isChecked(){
        return super.isChecked();
    }

    public void setStatus(boolean status){
        super.setStatus(status);
    }
}
