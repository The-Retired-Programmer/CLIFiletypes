package uk.theretiredprogrammer.clifiletypes.sh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import uk.theretiredprogrammer.clifiletypes.cli.ReportingTab;

public class ShFile {

    private static final String EMBEDDED_PARAMETER_PREFIX = "##! $";
    private final FileObject shFO;
    private final File shFile;
    private final LineComponents[] parameterinfo = new LineComponents[9];
    private int maxp = 0;

    public ShFile(FileObject shFO) {
        this.shFO = shFO;
        this.shFile = FileUtil.toFile(shFO);
    }

    public File getFile() {
        return shFile;
    }

    public String getPath() {
        return shFile.getPath();
    }

    public File getParentFile() {
       return FileUtil.toFile(shFO.getParent());
    } 

    public boolean extractParametersFromFile() {
        clearparameterinfo();
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(shFile))) {
                do {
                    String line = in.readLine();
                    if (line != null && line.startsWith(EMBEDDED_PARAMETER_PREFIX)) {
                        extractparameter(line.substring(EMBEDDED_PARAMETER_PREFIX.length()).trim());
                    } else {
                        maxp = getmaxparameters();
                        if (maxp > 0) {
                            boolean ok = CustomFormDialog.showFormDialog(parameterinfo, maxp);
                            if (ok) {
                                return true;
                            } else {
                                ReportingTab.cancelledMessage();
                                return false;
                            }
                        } else {
                            return true;
                        }
                    }
                } while (true);
            }
        } catch (IOException ex) {
            ReportingTab.exceptionMessage(ex);
        }
        return false;
    }

    public String[] getparameters() {
        if (maxp > 0) {
            String[] pvalue = new String[maxp];
            for (int i = 0; i < maxp; i++) {
                pvalue[i] = parameterinfo[i] == null ? "" : parameterinfo[i].val;
            }
            return pvalue;
        } else {
            return new String[]{};
        }
    }

    private void clearparameterinfo() {
        for (int i = 0; i < 9; i++) {
            parameterinfo[i] = null;
        }
    }

    private void extractparameter(String pline) {
        int equalspos = pline.indexOf('=');
        int bar3pos = pline.indexOf("|||", equalspos + 1);
        int pnum = Integer.parseInt(pline.substring(0, equalspos));
        if (pnum > 0 && pnum < 10) {
            parameterinfo[pnum - 1] = new LineComponents(pnum, pline.substring(equalspos + 1, bar3pos), pline.substring(bar3pos + 3));
        }
    }

    private int getmaxparameters() {
        int maxp = 0;
        for (int i = 8; i >= 0; i--) {
            if (parameterinfo[i] != null) {
                maxp = i + 1;
                break;
            }
        }
        return maxp;
    }

}
