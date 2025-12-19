package uk.theretiredprogrammer.clifiletypes.sh;

import java.io.File;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExtractedParameterActionsTest {

    public ExtractedParameterActionsTest() {
    }

    @Test
    public void testExtractParametersFromFile() {
        File scriptfile = new File("/app/code/CLIFiletypes/src/test/resources/testscript.sh");
        ShExtractedParameters params = new ShExtractedParameters();
        ExtractedParameterActions actions = new ExtractedParameterActions(params);
        actions.extractParametersFromFile(scriptfile);
        assertEquals("TESTING", params.getDialogtitle());
        assertEquals(2, params.getFieldCount());

        ShTextField field = (ShTextField) params.getField(1);
        assertEquals("another", field.getValue());
        assertEquals("deploy to instance:", field.getLabel());

        ShSelectField field2 = (ShSelectField) params.getField(2);
        assertEquals("dev", field2.getValue());
        assertEquals("select the instance:", field2.getLabel());

        assertEquals(3, params.getParamCount());
        ParamDefinition def = params.getParam(1);
        assertEquals(1, def.getParamno());
        assertEquals(2, def.getFieldno());
        assertEquals("", def.getAlternatives());
        def = params.getParam(2);
        assertEquals(2, def.getParamno());
        assertEquals(2, def.getFieldno());
        assertEquals("0,3,9", def.getAlternatives());

        String[] results = actions.evaluateParameters();
        assertEquals(3, results.length);
        assertEquals("dev", results[0]);
        assertEquals("0", results[1]);
        assertEquals("another", results[2]);
    }

//    @Test
//    public void testDisplayDialog() {
//    }
//    @Test
//    public void testGetparameters() {
//    }
}
