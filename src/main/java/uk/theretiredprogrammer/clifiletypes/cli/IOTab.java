package uk.theretiredprogrammer.clifiletypes.cli;

import org.netbeans.api.io.InputOutput;
import org.netbeans.api.io.OutputWriter;

public class IOTab {

    private final String iotabname;

    public IOTab(String iotabname) {
        this.iotabname = iotabname;
    }

    public OutputWriter getOut() {
        return InputOutput.get(iotabname, false).getOut();
    }

    public OutputWriter getErr() {
        return InputOutput.get(iotabname, false).getErr();
    }

    public IOTab reset() {
        InputOutput.get(iotabname, false).reset();
        return this;
    }

    public IOTab show() {
        InputOutput.get(iotabname, false).show();
        return this;
    }
    
    
}
