package com.objogate.wl.swing.driver;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.Component;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;
import org.hamcrest.TypeSafeMatcher;
import com.objogate.wl.Prober;
import com.objogate.wl.Query;
import com.objogate.wl.swing.ComponentManipulation;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.probe.ComponentIdentity;
import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;
import com.objogate.wl.swing.probe.RecursiveComponentFinder;
import com.objogate.wl.swing.probe.SingleComponentFinder;
import com.objogate.wl.swing.probe.TopLevelWindowFinder;

public class JComboBoxDriver extends JComponentDriver<JComboBox> implements ListDriver {
    public JComboBoxDriver(GesturePerformer gesturePerformer, ComponentSelector<JComboBox> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }

    public JComboBoxDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JComboBox> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JComboBoxDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JComboBox> componentType, Matcher<? super JComboBox>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public void selectItem(int index) {
        clickToOpenPopup();

        ComboPopupComponentDriver comboPopupDriver = new ComboPopupComponentDriver(this);

        comboPopupDriver.selectItem(index);
    }

    public void selectItem(Matcher<? extends Component> item) {
        clickToOpenPopup();

        ComboPopupComponentDriver comboPopupDriver = new ComboPopupComponentDriver(this);

        comboPopupDriver.selectItem(item);
    }

    private void clickToOpenPopup() {
        leftClickOnComponent();
    }

    public void hasSelectedIndex(int index) {
        has(new Query<JComboBox, Integer>() {
            public Integer query(JComboBox component) {
                return component.getSelectedIndex();
            }

            public void describeTo(Description description) {
                description.appendText("selected index");
            }
        }, equalTo(index));
    }

    private static class ComboPopupComponentDriver extends ComponentDriver<JComponent> {
        public ComboPopupComponentDriver(ComponentDriver<?> parent) {
            super(parent, 
                new SingleComponentFinder<JComponent>(
                    new RecursiveComponentFinder<JComponent>(JComponent.class, thatImplementsComboPopup(), 
                        new TopLevelWindowFinder())));
        }

        private static TypeSafeMatcher<JComponent> thatImplementsComboPopup() {
            return new TypeSafeMatcher<JComponent>() {
                @Override
                public boolean matchesSafely(JComponent component) {
                    return component instanceof ComboPopup;
                }

                public void describeTo(Description description) {
                    description.appendText("a component that implements ComboPopup");
                }
            };
        }

        public void selectItem(final int index) {
            JListFinderManipulation listFinderManipulation = new JListFinderManipulation();
            perform("selecting index in combo popup", listFinderManipulation);
            JListDriver listDriver = new JListDriver(this, ComponentIdentity.selectorFor(listFinderManipulation.list));
            listDriver.selectItem(index);
        }

        public void selectItem(Matcher<? extends Component> matcher) {
            JListFinderManipulation listFinderManipulation = new JListFinderManipulation();
            perform("selecting item in combo popup", listFinderManipulation);
            JListDriver listDriver = new JListDriver(this, selectorFor(listFinderManipulation.list));
            listDriver.selectItem(matcher);
        }

        //TODO: (nat) this should be done with a component finder or component selector
        private class JListFinderManipulation implements ComponentManipulation<JComponent> {
            JList list;
            
            public void manipulate(JComponent component) {
                ComboPopup popup = (ComboPopup) component;
                list = popup.getList();
            }
        }
    }

}
