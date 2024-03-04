package at.bbrz;

public class OutputHandler implements Output{

    @Override
    public void print(String output) {
        System.out.print(output);
    }

    @Override
    public void printLine(String output) {
        System.out.println(output);
    }

    @Override
    public void printLine(String output, String color) {
        OutputColors outputColor = OutputColors.findColor(color);
        System.out.println(outputColor.getLabel() + output + OutputColors.getResetLabel());
    }

    @Override
    public void emptyLine() {
        System.out.println();
    }
}
