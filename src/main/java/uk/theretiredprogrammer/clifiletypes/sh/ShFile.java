package uk.theretiredprogrammer.clifiletypes.sh;

import java.io.File;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class ShFile {

    private final FileObject shFO;
    private final File shFile;

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

//    private Linereader rdr;
//
//    private boolean isEmbeddedDialogLine(String line) {
//        if (line == null) {
//            return false;
//        }
//        rdr = new Linereader(line);
//        return rdr.isEmbeddedDialogLine();
//    }

//    public void extractParametersFromFile(ShExtractedParameters design) {
//        try {
//            try (BufferedReader in = new BufferedReader(new FileReader(shFile))) {
//                while (isEmbeddedDialogLine(in.readLine())) {
//                    parseline(design);
//                }
//            }
//        } catch (IOException ex) {
//            ReportingTab.exceptionMessage(ex);
//        }
//    }

//    public boolean displayDialog(ShExtractedParameters design) {
//        if (design.hasFields()) {
//            boolean ok = CustomFormDialog.showFormDialog(design);
//            if (ok) {
////                return true;
//                return false; // temporary
//            } else {
//                ReportingTab.cancelledMessage();
//                return false;
//            }
//        } else {
////            return true;
//            return false; // temporary
//        }
//    }

//    private void parseline(ShExtractedParameters design) {
//        rdr.getWord();
//        switch (rdr.getWord()) {
//            case "title" -> {
//                design.setDialogtitle(rdr.getPhrase());
//            }
//            case "textbox" -> {
//                design.addField(new ShTextField(rdr.getBracketedParameter()).setLabel(rdr.getPhrase()));
//            }
//            case "selection" -> {
//                design.addField(new ShSelectField(rdr.getBracketedParameters()).setLabel(rdr.getPhrase()));
//            }
//            case "" -> {
//                if (rdr.getChar() == '$') {
//                    // to be completed
//                }
//            }
//            default -> {
//                // need a reporting method here
//            }
//        }
//    }

//    public String[] getparameters(ShExtractedParameters design) {
//        return new String[]{}; // temporary
//    }

//    private void clearparameterinfo() {
//        for (int i = 0; i < 9; i++) {
//            parameterinfo[i] = null;
//        }
//    }
//    private void extractparameter(String pline) {
//        int equalspos = pline.indexOf('=');
//        int bar3pos = pline.indexOf("|||", equalspos + 1);
//        int pnum = Integer.parseInt(pline.substring(0, equalspos));
//        if (pnum > 0 && pnum < 10) {
//            parameterinfo[pnum - 1] = new LineComponents(pnum, pline.substring(equalspos + 1, bar3pos), pline.substring(bar3pos + 3));
//        }
//    }
//    private int getmaxparameters() {
//        int maxp = 0;
//        for (int i = 8; i >= 0; i--) {
//            if (parameterinfo[i] != null) {
//                maxp = i + 1;
//                break;
//            }
//        }
//        return maxp;
//    }
//    private class Linereader {
//
//        private static final String EMBEDDED_PARAMETER_PREFIX = "##! ";
//        private final String line;
//        private int pos;
//
//        public Linereader(String line) {
//            this.line = line;
//            pos = 0;
//        }
//
//        public boolean isEmbeddedDialogLine() {
//            if (line.startsWith(EMBEDDED_PARAMETER_PREFIX)) {
//                pos = EMBEDDED_PARAMETER_PREFIX.length();
//                return true;
//            }
//            return false;
//        }
//
//        private char getc(int p) {
//            return p >= line.length() ? '\n' : line.charAt(p);
//        }
//
//        private boolean isSpaceChar(int p) {
//            return getc(p) == ' ';
//        }
//
//        private boolean isCommaChar(int p) {
//            return getc(p) == ',';
//        }
//
//        private void skipSpaces() {
//            while (isSpaceChar(pos)) {
//                pos++;
//            }
//        }
//
//        private boolean isWordChar(int p) {
//            char c = getc(p);
//            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c > '0' && c <= '9');
//        }
//
//        private boolean isPhraseChar(int p) {
//            return isWordChar(p) || isSpaceChar(p);
//        }
//
//        private boolean isPhrasesChar(int p) {
//            return isWordChar(p) || isSpaceChar(p) || isCommaChar(p);
//        }
//
//        public String getWord() {
//            skipSpaces();
//            int start = pos;
//            while (isWordChar(pos)) {
//                pos++;
//            }
//            int end = pos;
//            skipSpaces();
//            return line.substring(start, end);
//        }
//
//        public String getPhrase() {
//            skipSpaces();
//            int start = pos;
//            while (isPhraseChar(pos)) {
//                pos++;
//            }
//            int end = pos;
//            skipSpaces();
//            return line.substring(start, end);
//        }
//
//        public String getPhrases() {
//            skipSpaces();
//            int start = pos;
//            while (isPhrasesChar(pos)) {
//                pos++;
//            }
//            int end = pos;
//            skipSpaces();
//            return line.substring(start, end);
//        }
//
//        public char getChar() {
//            return getc(pos++);
//        }
//
//        public String getBracketedParameter() {
//            String phrase;
//            if (getChar() == '(') {
//                phrase = getPhrase();
//                if (getChar() == ')') {
//                    return phrase;
//                }
//            }
//            // need some form of error reporting here
//            return "";
//        }
//
//        public String getBracketedParameters() {
//            String phrases;
//            if (getChar() == '{') {
//                phrases = getPhrases();
//                if (getChar() == '}') {
//                    return phrases;
//                }
//            }
//            // need some form of error reporting here
//            return "";
//        }
//    }

}
