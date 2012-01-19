package com.objogate.wl.swing.gesture;

import java.awt.Component;
import java.awt.Rectangle;
import org.hamcrest.Description;
import com.objogate.wl.Probe;
import com.objogate.wl.swing.ComponentSelector;

class ComponentScreenBoundsProbe implements Probe {
    private final ComponentSelector<? extends Component> componentSelector;

    public Rectangle bounds;


    public ComponentScreenBoundsProbe(ComponentSelector<? extends Component> componentSelector) {
        this.componentSelector = componentSelector;
    }

    public void probe() {
        componentSelector.probe();

        // if the component can't be found - probably not rendered yet
        if (componentSelector.components().isEmpty()) {
            bounds = null;
            return;
        }

        Component component = componentSelector.component();
        if (component.isShowing()) {
            bounds = new Rectangle(component.getLocationOnScreen(), component.getSize());
        } else {
            bounds = null;
        }
    }

    public boolean isSatisfied() {
        return bounds != null && bounds.getWidth() > 0 && bounds.getHeight() > 0;
    }

    public void describeTo(Description description) {
        description.appendText("screen dimensions of ");
        description.appendDescriptionOf(componentSelector);
    }

    public void describeFailureTo(Description description) {
        componentSelector.describeFailureTo(description);
        if (componentSelector.isSatisfied()) {
            description.appendText("\n    which had no screen dimensions");
        }
    }
}
