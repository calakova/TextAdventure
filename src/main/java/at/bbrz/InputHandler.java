package at.bbrz;

import java.util.Scanner;

public class InputHandler implements Input {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String getNextLine() {
        return scanner.nextLine();
    }
}
