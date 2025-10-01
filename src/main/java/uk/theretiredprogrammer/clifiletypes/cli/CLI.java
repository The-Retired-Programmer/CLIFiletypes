package uk.theretiredprogrammer.clifiletypes.cli;

import java.io.BufferedReader;
import java.io.IOException;
import org.netbeans.api.io.OutputWriter;
import org.openide.cookies.SaveCookie;
import org.openide.loaders.DataObject;

public class CLI {

    private ProcessBuilder pb;
    private final String[] command;

    public CLI(String... command) {
        this.command = command;
    }

    public CLI(String command1, String command2, String[] command3) {
        this.command = combine(command1, command2, command3);
    }

    public String[] getcommand() {
        return command;
    }

    private String[] combine(String part1, String part2, String[] part3) {
        String[] res = new String[part3.length + 2];
        res[0] = part1;
        res[1] = part2;
        System.arraycopy(part3, 0, res, 2, part3.length);
        return res;
    }

    public void execute(String tabname) {
        startMessage();
        IOTab executetab = new IOTab(tabname).reset();
        pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        try (OutputWriter outwriter = executetab.getOut()) {
            Process p = pb.start();
            try (BufferedReader in = p.inputReader()) {
                String line;
                while ((line = in.readLine()) != null) {
                    outwriter.println(line);
                }
                int rc;
                rc = p.waitFor();
                if (rc == 0) {
                    successMessage();
                } else {
                    errorMessage(rc);
                }
            }
        } catch (IOException | InterruptedException ex) {
            exceptionMessage(ex);
        }
    }

    public static void saveIfModified(DataObject dataobject) {
        try {
            if (dataobject.isModified()) {
                SaveCookie cookie = dataobject.getLookup().lookup(SaveCookie.class);
                if (cookie != null) {
                    cookie.save();
                }
            }
        } catch (IOException ex) {
            recoverableExceptionMessage(ex);
        }
    }

    private static IOTab reportingtab = null;

    private static IOTab getIOTab() {
        if (reportingtab == null) {
            reportingtab = new IOTab("External Processes Reporting").reset();
        }
        return reportingtab;
    }

    public void exceptionMessage(Exception ex) {
        getIOTab().getErr().println("Command terminated, reporting an exception: " + ex.getLocalizedMessage());
    }

    private static void recoverableExceptionMessage(Exception ex) {
        getIOTab().getErr().println("Command continueing with possible problems, recovered from an exception: " + ex.getLocalizedMessage());
    }

    public void errorMessage(int rc) {
        getIOTab().getErr().println("Command finished, reporting a return code " + rc);
    }

    public void successMessage() {
        getIOTab().getOut().println("Command finished sucessfully");

    }

    public void startMessage() {
        getIOTab().getOut().println(String.join(" ", command));
    }
}
