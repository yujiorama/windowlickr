package com.objogate.wl.swing.driver;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.anyOf;
import org.hamcrest.core.IsEqual;
import com.objogate.wl.Prober;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;
import static com.objogate.wl.swing.matcher.ComponentMatchers.withButtonText;
import com.objogate.wl.swing.matcher.JLabelTextMatcher;

/**
 * Driver for a JOptionPane
 * <p/>
 * <strong>You should make sure that the owner driver for this is for a top level frame,
 * otherwise this will not work as expected</strong>
 */
public class JOptionPaneDriver extends JComponentDriver<JOptionPane> {
    public JOptionPaneDriver(GesturePerformer gesturePerformer, ComponentSelector<JOptionPane> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }

    public JOptionPaneDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JOptionPane> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JOptionPaneDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JOptionPane> componentType, Matcher<? super JOptionPane>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public void clickOK() {
        clickButtonWithText("OK");
    }

    public void clickYes() {
        clickButtonWithText("Yes");
    }

    public void clickButtonWithText(String buttonText) {
        new AbstractButtonDriver<JButton>(this, the(JButton.class, withButtonText((buttonText)))).click();
    }

    public void typeText(String text) {
        JTextFieldDriver textField = new JTextFieldDriver(this, the(JTextField.class, ComponentDriver.named("OptionPane.textField")));
        textField.moveMouseToCenter();
        textField.typeText(text);
    }

    @SuppressWarnings("unchecked")
    public void selectValue(String value) {
        GeneralListDriver driver = new GeneralListDriver(this, JComponent.class, anyOf(ComponentDriver.named("OptionPane.comboBox"), ComponentDriver.named("OptionPane.list")));
        driver.selectItem(new JLabelTextMatcher(new IsEqual<String>(value)));
        clickOK();
    }
}
