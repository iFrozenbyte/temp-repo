package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString() {
        System.out.print(">");
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    public int readInt() {
        String line = readString();
        if (line == null) {
            throw new IllegalStateException("Input closed");
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw  new IllegalArgumentException("invalid number " +  line);
        }
    }
}