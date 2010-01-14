package com.prabowomurti.guwekatejugeape;

/**
 * @author Prabowo Murti
 */

import com.prabowomurti.*;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class GuwekatejugeapeMidlet extends MIDlet {
    public void startApp() {
		Display.getDisplay (this).setCurrent (new GuwekatejugeapeCanvas(this));
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

	public void exitMidlet(){
		destroyApp(true);
		notifyDestroyed();
	}
}
