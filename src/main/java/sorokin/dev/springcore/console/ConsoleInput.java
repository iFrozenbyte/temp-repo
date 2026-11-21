package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Единая точка чтения ввода
 */

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
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

}