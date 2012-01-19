package com.objogate.wl.swing.driver.tests;

import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import com.objogate.wl.swing.driver.AbstractButtonDriver;

public class JButtonDriverTest extends AbstractButtonDriverTest<JButton, AbstractButtonDriver<JButton>> {
    @Before
    public void createButtonAndDriver() throws Exception {
        button = new JButton("button");

        view(button);

        driver = new AbstractButtonDriver<JButton>(gesturePerformer, selectorFor(button), prober);
    }
    
    @Test
    public void testNothing() {
        //only here because junit 4 won't run the tests in the superclass without it
    }
}
