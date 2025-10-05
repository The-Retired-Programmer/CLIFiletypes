package uk.theretiredprogrammer.clifiletypes.sh;

import javax.swing.*;
import java.awt.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public class CustomFormDialog {

    public static boolean showFormDialog(LineComponents[] lines, int maxlines) {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField[] textFields = new JTextField[maxlines];

        gbc.gridx = 0;
        gbc.gridy = 0;
                panel.add(new JLabel("Description"), gbc);
        gbc.gridx = 1;
                panel.add(new JLabel("Parameter"), gbc);
        gbc.gridx = 2;
                panel.add(new JLabel("Value"), gbc);
        

        for (int i = 0; i < maxlines; i++) {
            if (lines[i] != null) {
                gbc.gridx = 0;
                gbc.gridy = i+1;
                panel.add(new JLabel(lines[i].description), gbc);

                gbc.gridx = 1;
                panel.add(new JLabel("$"+String.valueOf(i + 1)), gbc);

                gbc.gridx = 2;
                textFields[i] = new JTextField(lines[i].val, 15);
                panel.add(textFields[i], gbc);
            }
        }

        NotifyDescriptor nd = new NotifyDescriptor(
                panel,
                "Set Parameter Values",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                null,
                NotifyDescriptor.OK_OPTION
        );

        Object result = DialogDisplayer.getDefault().notify(nd);

        if (NotifyDescriptor.OK_OPTION.equals(result)) {
            // Retrieve values from text maxlines
            for (int i = 0; i < maxlines; i++) {
                if (lines[i] != null) {
                    lines[i].val = textFields[i].getText();
                }
            }
            return true;
        }
        return false;
    }
}
