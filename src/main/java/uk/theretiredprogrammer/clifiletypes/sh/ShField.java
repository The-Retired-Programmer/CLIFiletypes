package uk.theretiredprogrammer.clifiletypes.sh;

import javax.swing.JComponent;

public abstract class ShField<C extends JComponent> {

    private String label = "";

    public String getLabel() {
        return label;
    }

    public ShField setLabel(String label) {
        this.label = label;
        return this;
    }

    public abstract String getValue();
    
    public abstract String getAlternativeValue(String alternatives);

    public abstract C getComponent();

}
