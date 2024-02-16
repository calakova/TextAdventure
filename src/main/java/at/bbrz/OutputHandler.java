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

    // TODO: Implement this method
    @Override
    public void printLine(String output, String color) {

    }

    @Override
    public void emptyLine() {
        System.out.println();
    }
}
