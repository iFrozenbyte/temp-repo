package sorokin.dev.springcore.console;

import org.springframework.stereotype.Component;
import sorokin.dev.springcore.account.Account;
import sorokin.dev.springcore.account.AccountService;
import sorokin.dev.springcore.operations.OperationCommand;
import sorokin.dev.springcore.user.UserService;

import static sorokin.dev.springcore.Constant.commands;

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
        System.out.println("> MiniBank started. Type 'EXIT' to stop.");
        while (true) {
            try {
                System.out.println("\nEnter command:\n" + commands);
                String input = consoleInput.readString();
                if (input == null || input.isEmpty()) {
                    return;
                }
                OperationCommand operationCommand = OperationCommand.valueOf(input.trim().toUpperCase());

                switch (operationCommand) {
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

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input");
            }
        }
    }

    private void handleUserCreate() {
        System.out.println("Please input login:");
        String login = consoleInput.readString();
        if (login == null) {
            return;
        }
        try {
            userService.createUser(login);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleAccountCreate() {
        try {
            System.out.println("Please input user id:");
            int userId = consoleInput.readInt();
            Account account = userService.createAdditionalAccount(userId);
            System.out.println("Account created: " + account);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleDepositCreate() {

        try {
            System.out.println("Please enter account id: ");
            int accountId = consoleInput.readInt();

            System.out.println("Please enter amount to be deposited: ");
            int amount = consoleInput.readInt();

            accountService.setAccountDeposit(accountId, amount);
            System.out.println("Deposit completed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleWithdraw() {

        try {
            System.out.println("input account id:");
            int accountId = consoleInput.readInt();

            System.out.println("input amount to withdraw:");
            int amount = consoleInput.readInt();

            accountService.setAccountWithdraw(accountId, amount);
            System.out.println("Withdrawal completed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleTransfer() {

        try {
            System.out.println("Please input sender account id:");
            int senderAccountId = consoleInput.readInt();

            System.out.println("Please input recipient account id:");
            int recipientAccountId = consoleInput.readInt();

            System.out.println("Please input amount to be transferred:");
            int amount = consoleInput.readInt();

            accountService.transferMoney(senderAccountId, recipientAccountId, amount);
            System.out.println("Transfer completed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleClose() {
        try {
            System.out.println("Please input account id:");
            int accountId = consoleInput.readInt();
            userService.closeAccount(accountId);
            System.out.println("Account closed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}