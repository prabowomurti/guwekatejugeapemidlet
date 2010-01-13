package com.prabowomurti;

/**
 * @author Prabowo Murti
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class GuwekatejugeapeMidlet extends MIDlet {
    public void startApp() {
		Display.getDisplay (this).setCurrent (new GuwekatejugeapeCanvas ());
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
