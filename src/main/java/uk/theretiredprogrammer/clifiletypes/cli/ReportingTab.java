package uk.theretiredprogrammer.clifiletypes.cli;

public class ReportingTab {

    private static IOTab reportingtab = null;

    private static IOTab getIOTab() {
        if (reportingtab == null) {
            reportingtab = new IOTab("External Processes Reporting").reset();
        }
        return reportingtab;
    }

    public static void exceptionMessage(Exception ex) {
        getIOTab().getErr().println("Command terminated, reporting an exception: " + ex.getLocalizedMessage());
    }

    public static void recoverableExceptionMessage(Exception ex) {
        getIOTab().getErr().println("Command continueing with possible problems, following an exception: " + ex.getLocalizedMessage());
    }

    public static void errorMessage(int rc) {
        getIOTab().getErr().println("Command finished, reporting a return code " + rc);
    }

    public static void successMessage() {
        getIOTab().getOut().println("Command finished sucessfully");
    }

    public static void cancelledMessage() {
        getIOTab().getOut().println("Command cancelled by user");
    }

    public static void startMessage(String... command) {
        getIOTab().getOut().println(String.join(" ", command));
    }
}
