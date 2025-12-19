package uk.theretiredprogrammer.clifiletypes.sh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import uk.theretiredprogrammer.clifiletypes.cli.ReportingTab;
import static uk.theretiredprogrammer.clifiletypes.sh.ShExtractedParameters.UNDEFINEDPARAMNO;

public class ExtractedParameterActions {

    private final ShExtractedParameters design;

    public ExtractedParameterActions() {
        this(new ShExtractedParameters());
    }

    public ExtractedParameterActions(ShExtractedParameters design) {
        this.design = design;
    }

    public void extractParametersFromFile(File scriptfile) {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(scriptfile))) {
                while (isEmbeddedDialogLine(in.readLine())) {
                    parseline(design);
                }
            }
        } catch (IOException ex) {
            ReportingTab.exceptionMessage(ex);
        }
    }

    public boolean displayDialog() {
        if (design.getFieldCount() > 0) {
            boolean ok = CustomFormDialog.showFormDialog(design);
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

    public String[] evaluateParameters() {
        int count = design.getParamCount();
        String[] parameters = new String[count];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = evaluateparameter(i + 1);
        }
        return parameters;
    }

    private Linereader rdr;

    private boolean isEmbeddedDialogLine(String line) {
        if (line == null) {
            return false;
        }
        rdr = new Linereader(line);
        return rdr.isEmbeddedDialogLine();
    }

    private void parseline(ShExtractedParameters design) {
        switch (rdr.getWord()) {
            case "title" -> {
                design.setDialogtitle(rdr.getRestOfLine());
            }
            case "textbox" -> {
                design.addField(new ShTextField(rdr.getBracketedParameter()).setLabel(rdr.getRestOfLine()));
            }
            case "selection" -> {
                design.addField(new ShSelectField(rdr.getBracketedParameters()).setLabel(rdr.getRestOfLine()));
            }
            case "" -> {
                if (rdr.getChar() == '$') {
                    design.addParam(rdr.getNumericChar(), rdr.getFieldNum(), rdr.getBracketedParameters());
                }
            }
            default -> {
                // need a reporting method here
            }
        }
    }

    private String evaluateparameter(int paramno) {
        ParamDefinition pdef = design.getParam(paramno);
        if (pdef.getParamno() == UNDEFINEDPARAMNO) {
            return "";
        }
        if (pdef.getParamno() != paramno) {
            return ""; // temporary - should throw an error
        }
        ShField field = design.getField(pdef.getFieldno());
        String alternatives = pdef.getAlternatives();
        return alternatives.isBlank() ? field.getValue() : field.getAlternativeValue(alternatives);
    }

    private class Linereader {

        private static final String EMBEDDED_PARAMETER_PREFIX = "##! ";
        private final String line;
        private int pos;

        public Linereader(String line) {
            this.line = line;
            pos = 0;
        }

        public boolean isEmbeddedDialogLine() {
            if (line.startsWith(EMBEDDED_PARAMETER_PREFIX)) {
                pos = EMBEDDED_PARAMETER_PREFIX.length();
                return true;
            }
            return false;
        }

        private char getc(int p) {
            return p >= line.length() ? '\n' : line.charAt(p);
        }

        private boolean isSpaceChar(int p) {
            return getc(p) == ' ';
        }

        private boolean isCommaChar(int p) {
            return getc(p) == ',';
        }

        private void skipSpaces() {
            while (isSpaceChar(pos)) {
                pos++;
            }
        }

        private boolean isWordChar(int p) {
            char c = getc(p);
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
        }

        private boolean isPhraseChar(int p) {
            return isWordChar(p) || isSpaceChar(p);
        }

        private boolean isPhrasesChar(int p) {
            return isWordChar(p) || isSpaceChar(p) || isCommaChar(p);
        }

        public String getWord() {
            skipSpaces();
            int start = pos;
            while (isWordChar(pos)) {
                pos++;
            }
            int end = pos;
            skipSpaces();
            return line.substring(start, end);
        }

        public String getPhrase() {
            skipSpaces();
            int start = pos;
            while (isPhraseChar(pos)) {
                pos++;
            }
            int end = pos;
            skipSpaces();
            return line.substring(start, end);
        }

        public String getPhrases() {
            skipSpaces();
            int start = pos;
            while (isPhrasesChar(pos)) {
                pos++;
            }
            int end = pos;
            skipSpaces();
            return line.substring(start, end);
        }

        public String getRestOfLine() {
            return line.substring(pos, line.length()).trim();
        }

        public char getChar() {
            return getc(pos++);
        }

        public int getNumericChar() {
            char c = getChar();
            if (c >= '1' && c <= '9') {
                return c - '0';
            }
            // need some form of error reporting
            return 1;
        }

        public int getFieldNum() {
            skipSpaces();
            if (getChar() == '=') {
                skipSpaces();
                if (getChar() == '#') {
                    return getNumericChar();
                }
            }
            // need some form of error reporting here
            return '1';
        }

        public String getBracketedParameter() {
            String phrase;
            if (getChar() == '(') {
                phrase = getPhrase();
                if (getChar() == ')') {
                    return phrase;
                }
            }
            // need some form of error reporting here
            return "";
        }

        public String getBracketedParameters() {
            skipSpaces();
            String phrases;
            if (getChar() == '{') {
                phrases = getPhrases();
                if (getChar() == '}') {
                    return phrases;
                }
            }
            // need some form of error reporting here
            return "";
        }
    }

}
