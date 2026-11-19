package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;
import sorokin.dev.springcore.account.Account;
import sorokin.dev.springcore.account.AccountService;
import sorokin.dev.springcore.operations.OperationCommand;
import sorokin.dev.springcore.user.User;
import sorokin.dev.springcore.user.UserService;

import java.util.Scanner;

/**
 * Слушает консольный ввод и вызывает нужные методы сервисов
 */

@Component
public class OperationsConsoleListener {

    private final UserService userService;
    private final AccountService accountService;
    private final ConsoleInput consoleInput;


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
                    case USER_CREATE:
                        handleUserCreate();
                        continue;

                    case SHOW_ALL_USERS:
                        userService.getUsers();
                        continue;

                    case ACCOUNT_CREATE:
                        handleAccountCreate();
                        continue;

                    case ACCOUNT_DEPOSIT:
                        handleDepositCreate();
                        continue;

                    case ACCOUNT_WITHDRAW:
                        handleWithdraw();
                        continue;

                    case ACCOUNT_TRANSFER:
                        handleTransfer();
                        continue;

                    case ACCOUNT_CLOSE:
                        handleClose();
                        continue;

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
        userService.createUser(login);
    }

    private void handleAccountCreate() {
        try {
            int userId = consoleInput.readInt(1);
            Account account = userService.createAdditionalAccount(userId);
            System.out.println("Account created: " + account);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleDepositCreate() {
        int accountId = consoleInput.readInt(2);
        int amount = consoleInput.readInt(3);
        try {
            accountService.setAccountDeposit(accountId, amount);
            System.out.println("Deposit completed successfully.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleWithdraw() {
        int accountId = consoleInput.readInt(6);
        int amount = consoleInput.readInt(7);
        try {
            accountService.setAccountWithdraw(accountId, amount);
            System.out.println("Withdrawal completed successfully.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleTransfer() {
        int senderAccountId = consoleInput.readInt(4);
        int receiverAccountId = consoleInput.readInt(5);
        int amount = consoleInput.readInt(3);

        try {
            accountService.transferMoney(senderAccountId, receiverAccountId, amount);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Transfer completed successfully.");
    }

    private void handleClose() {
        try {
            int accountId = consoleInput.readInt(2);
            accountService.closeAccount(accountId);
            System.out.println("Account closed successfully.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }
}