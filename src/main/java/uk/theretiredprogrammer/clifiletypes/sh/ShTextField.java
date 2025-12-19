package uk.theretiredprogrammer.clifiletypes.sh;

import javax.swing.JTextField;

public class ShTextField extends ShField<JTextField> {

    private final JTextField component;

    public ShTextField(String value) {
        this.component = new JTextField(value); 
    }

    @Override
    public String getValue() {
        return component.getText();
    }

    @Override
    public JTextField getComponent() {
        return component;
    }

    @Override
    public String getAlternativeValue(String alternatives) {
        // throw an error ?
        return getValue();
    }

}
