package uk.theretiredprogrammer.clifiletypes.sh;

import java.util.ArrayList;
import java.util.List;

public class ShExtractedParameters {

    public static final int UNDEFINEDPARAMNO = 0;
    private static final ParamDefinition UNDEFINEDPARAMETERDEF = new ParamDefinition(UNDEFINEDPARAMNO , 0, "");
    private String dialogtitle;
    private final List<ShField> fields;
    private final ParamDefinition[] params;

    public ShExtractedParameters() {
        fields = new ArrayList<>();
        params = new ParamDefinition[9];
        for (int i = 0; i < params.length; i++) {
            params[i] = UNDEFINEDPARAMETERDEF;
        }
        dialogtitle = "Define parameters for script";
    }

    public String getDialogtitle() {
        return dialogtitle;
    }

    public void setDialogtitle(String dialogtitle) {
        this.dialogtitle = dialogtitle;
    }

    public int getFieldCount() {
        return fields.size();
    }

    public ShField getField(int fieldno) {
        return fields.get(fieldno - 1);
    }

    public void addField(ShField field) {
        fields.add(field);
    }

    public void addParam(int paramno, int fieldno, String alternatives) {
        params[paramno - 1] = new ParamDefinition(paramno, fieldno, alternatives);
    }

    public ParamDefinition getParam(int i) {
        return params[i - 1];
    }

    public int getParamCount() {
        for (int i = params.length - 1; i >= 0; i--) {
            if (params[i].getParamno() != UNDEFINEDPARAMNO) {
                return i + 1;
            }
        }
        return 0;
    }
}
