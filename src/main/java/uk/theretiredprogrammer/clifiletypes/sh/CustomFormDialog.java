package uk.theretiredprogrammer.clifiletypes.sh;

import javax.swing.*;
import java.awt.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public class CustomFormDialog {

    public static boolean showFormDialog(ShExtractedParameters design) {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        for (int row = 1; row <= design.getFieldCount(); row++) {
            ShField field = design.getField(row);
            gbc.gridx = 0;
            gbc.gridy = row;
            panel.add(new JLabel(field.getLabel()), gbc);
            gbc.gridx = 1;
            panel.add(field.getComponent(), gbc);
        }

        NotifyDescriptor nd = new NotifyDescriptor(
                panel,
                design.getDialogtitle(),
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                null,
                NotifyDescriptor.OK_OPTION
        );

        Object result = DialogDisplayer.getDefault().notify(nd);

        if (NotifyDescriptor.OK_OPTION.equals(result)) {
            return true;
        }
        return false;
    }
}
