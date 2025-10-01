package uk.theretiredprogrammer.clifiletypes.pgsql;

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
        id = "uk.theretiredprogrammer.clifiletypes.pgsql.ExecutePGSQLFile"
)
@ActionRegistration(
        displayName = "#CTL_ExecuteFile"
)
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-pgsql/Actions", position = 150),
    @ActionReference(path = "Editors/text/x-pgsql/Popup", position = 100)
})
@Messages("CTL_ExecuteFile=Execute")
public final class PGSQLAction implements ActionListener, Runnable {

    private final List<DataObject> context;

    public PGSQLAction(List<DataObject> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        RequestProcessor rp = new RequestProcessor("text-x-pgsql_execute");
        rp.post(this);
    }

    @Override
    public void run() {
        for (DataObject dataObject : context) {
            CLI.saveIfModified(dataObject);
            PgsqlFile input = new PgsqlFile(dataObject.getPrimaryFile());
            CLI cli = new CLI("psql", "-w", "-P", "pager", "--dbname=" + input.getDbname(), "--file=" + input.getPath());
            cli.execute("pgsql execution");
        }
    }
}
