package uk.theretiredprogrammer.clifiletypes.sh;

import javax.swing.JComboBox;

public class ShSelectField extends ShField<JComboBox> {

    private final JComboBox<String> component;

    public ShSelectField(String selections) {
        this(commasplitter(selections));
    }

    private static String[] commasplitter(String string) {
        String[] split = string.split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return split;
    }

    public ShSelectField(String[] selections) {
        component = new JComboBox(selections);
        component.setSelectedIndex(0);
    }

    private int getSelectedIndex() {
        int i = component.getSelectedIndex();
        return i == -1 ? 0 : i;
    }

    @Override
    public String getValue() {
        return component.getItemAt(getSelectedIndex());
    }

    @Override
    public String getAlternativeValue(String alternatives) {
        String[] alttexts = commasplitter(alternatives);
        int i = getSelectedIndex();
        return alttexts.length > i ? alttexts[i] : getValue();
    }

    @Override
    public JComboBox getComponent() {
        return component;
    }

}
