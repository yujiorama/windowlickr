package com.objogate.wl.swing.driver;

import java.awt.Component;

import javax.swing.JTextField;

import org.hamcrest.Matcher;

import com.objogate.wl.Prober;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class JTextFieldDriver extends JTextComponentDriver<JTextField> {
    public JTextFieldDriver(GesturePerformer gesturePerformer, ComponentSelector<JTextField> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }
    
    public JTextFieldDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JTextField> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JTextFieldDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JTextField> componentType, Matcher<? super JTextField>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }
}
