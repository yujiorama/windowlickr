package com.objogate.wl.swing.driver;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import com.objogate.exception.Defect;
import com.objogate.wl.Prober;
import com.objogate.wl.Query;
import static com.objogate.wl.gesture.Gestures.*;
import com.objogate.wl.gesture.Tracker;
import com.objogate.wl.swing.ComponentManipulation;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class JTabbedPaneDriver extends JComponentDriver<JTabbedPane> {
    public JTabbedPaneDriver(GesturePerformer gesturePerformer, ComponentSelector<JTabbedPane> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }

    public JTabbedPaneDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JTabbedPane> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JTabbedPaneDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JTabbedPane> componentType, Matcher<? super JTabbedPane>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }

    public void hasTabCount(int expectedCount) {
        has(new Query<JTabbedPane, Integer>() {
            public Integer query(JTabbedPane component) {
                return component.getTabCount();
            }

            public void describeTo(Description description) {
                description.appendText("tab count");
            }
        }, Matchers.equalTo(expectedCount));
    }

    public void selectTab(final int index) {

        performGesture(moveMouseTo(new Tracker() {
            public Point target(Point currentLocation) {
                final SelectedTabManipulation tabManipulation = new SelectedTabManipulation(index);
                perform("finding tab", tabManipulation);
                return tabManipulation.startLocation;
            }
        }), clickMouseButton(BUTTON1));
    }

    public void hasSelectedTab(int expectedIndex) {
        has(new Query<JTabbedPane, Integer>() {
            public Integer query(JTabbedPane component) {
                return component.getSelectedIndex();
            }

            public void describeTo(Description description) {
                description.appendText("selected tab");
            }
        }, Matchers.equalTo(expectedIndex));
    }

    private static class SelectedTabManipulation implements ComponentManipulation<JTabbedPane> {
        private final int index;
        private Point startLocation;

        public SelectedTabManipulation(int index) {
            this.index = index;
        }

        public void manipulate(JTabbedPane component) {
            Rectangle rectangle = component.getBounds();
            int tabPlacement = component.getTabPlacement();
            int randomOffset;
            int randomInset = 5;
            if (tabPlacement == SwingConstants.LEFT) {
                randomOffset = randomInset;
            } else if (tabPlacement == SwingConstants.RIGHT) {
                randomOffset = rectangle.width - randomInset;
            } else if (tabPlacement == SwingConstants.TOP) {
                randomOffset = randomInset;
            } else if (tabPlacement == SwingConstants.BOTTOM) {
                randomOffset = rectangle.height - randomInset;
            } else {
                throw new Defect("");
            }

            if (tabPlacement == SwingConstants.LEFT || tabPlacement == SwingConstants.RIGHT) {
                for (int y = 0; y < rectangle.getHeight(); y++) {
                    int i = component.indexAtLocation(randomOffset, y);
                    if (i == this.index) {
                        Point screen = component.getLocationOnScreen();
                        this.startLocation = screen;
                        screen.translate(randomOffset, y);
                        return;
                    }
                }
            } else {
                for (int x = 0; x < rectangle.getWidth(); x++) {
                    int i = component.indexAtLocation(x, randomOffset);
                    if (i == this.index) {
                        Point screen = component.getLocationOnScreen();
                        this.startLocation = screen;
                        screen.translate(x, randomOffset);
                        return;
                    }
                }
            }

            throw new Defect("Cannot find tab with index " + index);
        }
    }
}
