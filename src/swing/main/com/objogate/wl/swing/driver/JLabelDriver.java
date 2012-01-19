package com.objogate.wl.swing.driver;

import javax.swing.JLabel;
import java.awt.Component;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import com.objogate.wl.Prober;
import com.objogate.wl.Query;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.internal.query.TextQuery;

public class JLabelDriver extends JComponentDriver<JLabel> implements TextQuery {
    public JLabelDriver(ComponentDriver<? extends Component> containerDriver, Matcher<? super JLabel>... matchers) {
        super(containerDriver, JLabel.class, matchers);
    }

    public JLabelDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JLabel> componentSelector) {
        super(parentOrOwner, componentSelector);
    }
    
    public JLabelDriver(GesturePerformer gesturePerformer, ComponentSelector<JLabel> selector, Prober prober) {
        super(gesturePerformer, selector, prober);
    }

    public void hasText(Matcher<String> matcher) {
        has(text(), matcher);
    }

    private static Query<JLabel, String> text() {
        return new Query<JLabel, String>() {
            public String query(JLabel label) {
                return label.getText();
            }

            public void describeTo(Description description) {
                description.appendText("label text");
            }
        };
    }

}
