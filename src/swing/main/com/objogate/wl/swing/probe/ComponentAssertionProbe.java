package com.objogate.wl.swing.probe;

import java.awt.Component;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import com.objogate.wl.swing.ComponentFinder;
import com.objogate.wl.swing.ComponentSelector;

public class ComponentAssertionProbe<T extends Component> implements ComponentFinder<T> {
    private final ComponentSelector<T> selector;
    private final Matcher<? super T> assertion;

    private boolean assertionMet = false;

    public ComponentAssertionProbe(ComponentSelector<T> selector, Matcher<? super T> assertion) {
        this.assertion = assertion;
        this.selector = selector;
    }

    public java.util.List<T> components() {
        return selector.components();
    }

    public void probe() {
        selector.probe();
        assertionMet = selector.isSatisfied() && assertion.matches(selector.component());
    }

    public boolean isSatisfied() {
        return assertionMet;
    }

    public void describeTo(Description description) {
        description.appendDescriptionOf(selector)
                .appendText("\nand check that it is ")
                .appendDescriptionOf(assertion);
    }

    public void describeFailureTo(Description description) {
        selector.describeFailureTo(description);
        if (selector.isSatisfied()) {
            description.appendText("\n   it ")
                    .appendText(assertionMet ? "is " : "is not ")
                    .appendDescriptionOf(assertion);
        }
    }
}
