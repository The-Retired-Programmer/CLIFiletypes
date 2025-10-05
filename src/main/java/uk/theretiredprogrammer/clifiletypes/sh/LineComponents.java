package uk.theretiredprogrammer.clifiletypes.sh;

public class LineComponents {

    public final int num;
    public final String description;
    public String val;

    public LineComponents(int num, String defaultval, String description) {
        this.num = num;
        this.description = description;
        this.val = defaultval;
    }
}
