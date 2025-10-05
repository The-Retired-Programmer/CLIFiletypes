package uk.theretiredprogrammer.clifiletypes.cli;

import java.io.BufferedReader;
import java.io.File;
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

    public void execute(File parent, String tabname) {
        ReportingTab.startMessage(command);
        IOTab executetab = new IOTab(tabname).reset();
        pb = new ProcessBuilder(command).directory(parent).redirectErrorStream(true);
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
                    ReportingTab.successMessage();
                } else {
                    ReportingTab.errorMessage(rc);
                }
            }
        } catch (IOException | InterruptedException ex) {
            ReportingTab.exceptionMessage(ex);
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
            ReportingTab.recoverableExceptionMessage(ex);
        }
    }
}
