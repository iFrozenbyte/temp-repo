package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;
import sorokin.dev.springcore.account.Account;
import sorokin.dev.springcore.account.AccountService;
import sorokin.dev.springcore.operations.OperationCommand;
import sorokin.dev.springcore.user.User;
import sorokin.dev.springcore.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Слушает консольный ввод и вызывает нужные методы сервисов
 */

@Component
public class OperationsConsoleListener {

    private final UserService userService;
    private final AccountService accountService;
    private final ConsoleInput  consoleInput;


    public OperationsConsoleListener(
            UserService userService,
            AccountService accountService,
            ConsoleInput consoleInput
    ) {
        this.userService = userService;
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    public void startService() {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("> MiniBank started. Type 'EXIT' to stop.\n\nAvailable commands:\n" +
                    "USER_CREATE, SHOW_ALL_USERS, ACCOUNT_CREATE, ACCOUNT_DEPOSIT,\n" +
                    "ACCOUNT_WITHDRAW, ACCOUNT_TRANSFER, ACCOUNT_CLOSE, EXIT\n");
            while (true) {
                System.out.println("Enter command:");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String line = scanner.nextLine().trim().toUpperCase();
                if (line.isEmpty()) {
                    continue;
                }

                OperationCommand command;
                try {
                    command = OperationCommand.valueOf(line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input: " + line);
                    continue;
                }

                switch (command) {
                    case USER_CREATE:handleUserCreate();

                    case SHOW_ALL_USERS:userService.getUsers().forEach(System.out::println);

                    case ACCOUNT_CREATE:handleAccountCreate();
                        ;
                    case ACCOUNT_DEPOSIT:handleDepositCreate();
                        ;
                    case ACCOUNT_WITHDRAW:handleWithdraw();
                        ;
                    case ACCOUNT_TRANSFER:
                        ;
                    case ACCOUNT_CLOSE:
                        ;
                    case EXIT:
                        return;
                    default:
                        System.out.println("Invalid input");
                }

            }
        }
    }

    private void handleUserCreate() {
        String login = consoleInput.readString();
        User user = userService.createUser(login);
        System.out.println("User created: " + user);
    }

    private void handleAccountCreate() {
        int userId = consoleInput.readInt();
        Account account = accountService.createAccount(userId);
        System.out.println("Account created: " + account);
    }

    private void handleDepositCreate() {
        int accountId = consoleInput.readInt();
        int amount = consoleInput.readInt();
        accountService.setAccountDeposit(accountId, amount);
        System.out.println("Deposit completed successfully.");
    }

    private void handleWithdraw() {
        int accountId = consoleInput.readInt();
        int amount = consoleInput.readInt();
        accountService.setAccountWithdraw(accountId, amount);
        System.out.println("Withdrawal completed successfully.");
    }
}