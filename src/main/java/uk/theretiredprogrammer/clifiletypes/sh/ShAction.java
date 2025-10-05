package uk.theretiredprogrammer.clifiletypes.sh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import uk.theretiredprogrammer.clifiletypes.cli.CLI;

@ActionID(
        category = "Build",
        id = "uk.theretiredprogrammer.clifiletypes.sh.ExecuteShFile"
)
@ActionRegistration(
        displayName = "#CTL_ExecuteFile"
)
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-sh/Actions", position = 150),
    @ActionReference(path = "Editors/text/x-sh/Popup", position = 100)
})
@Messages("CTL_ExecuteFile=Execute")
public final class ShAction implements ActionListener, Runnable {

    private final List<DataObject> context;

    public ShAction(List<DataObject> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        RequestProcessor rp = new RequestProcessor("text-x-sh_execute");
        rp.post(this);
    }

    @Override
    public void run() {
        for (DataObject dataObject : context) {
            CLI.saveIfModified(dataObject);
            ShFile script = new ShFile(dataObject.getPrimaryFile());
            if (!script.extractParametersFromFile()) {
                return;
            }
            CLI cli = new CLI("bash", script.getPath(), script.getparameters());
            cli.execute(script.getParentFile(),"sh execution");
        }
    }
}
