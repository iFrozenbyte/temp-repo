package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 *   Единая точка чтения и валидации ввода
 */

@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public String readString(){
        System.out.println("Please enter your login: ");
        return scanner.nextLine();
    }

    public int readInt(){
        System.out.println("Please enter user ID: ");
        return scanner.nextInt();
    }
}