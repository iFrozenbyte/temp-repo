package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Единая точка чтения и валидации ввода
 */

@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public String readString() {
        System.out.println("Please enter your login: ");
        return scanner.nextLine();
    }

    public int readInt(int inputType) {
        return switch (inputType) {
            case 1 -> {
                System.out.println("Please enter user id: ");
                yield scanner.nextInt();
            }
            case 2 -> {
                System.out.println("Please enter account id: ");
                yield scanner.nextInt();
            }
            case 3 -> {
                System.out.println("Please enter amount to deposit: ");
                yield scanner.nextInt();
            }
            case 4 -> {
                System.out.println("Please enter sender account id: ");
                yield scanner.nextInt();
            }
            case 5 -> {
                System.out.println("Please enter receiver account id: ");
                yield scanner.nextInt();
            }
            case 6 -> {
                System.out.println("Please enter account to withdraw: ");
                yield scanner.nextInt();
            }
            case 7 -> {
                System.out.println("Please enter amount to withdraw: ");
                yield scanner.nextInt();
            }
            default -> 0;
        };
    }
}