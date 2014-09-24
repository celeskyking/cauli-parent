package org.cauli.ui;

import org.cauli.junit.TestPlan;
import org.cauli.ui.selenium.browser.Engine;

/**
 * Created by tianqing.wang on 2014/9/11
 */
public class CauliUIConfig extends TestPlan{

    private Engine[] engines;

    public Engine[] getEngines() {
        return engines;
    }

    public void setEngines(Engine[] engines) {
        this.engines = engines;
    }
}
