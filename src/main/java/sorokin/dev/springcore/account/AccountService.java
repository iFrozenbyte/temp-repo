package sorokin.dev.springcore.account;

import org.springframework.stereotype.Service;
import sorokin.dev.springcore.user.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final AccountProperties  accountProperties;
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final UserService userService;
    private int nextAccountId = 1;

    public AccountService(AccountProperties accountProperties, UserService userService) {
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public Account createAccount(int userId) {
        if (!hasAccountForUser(userId)) {
            throw new IllegalStateException("User " + userId + " not found.");
        }
        int accountId = generateId();
        Account account = new Account(accountId, userId, accountProperties.getDefaultAmount());
        accounts.put(accountId, account);

        return account;
    }

    public void setAccountDeposit(int accountId, int amount) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account " + accountId + " not found.");
        }
        Account account = accounts.get(accountId);
        account.setMoneyAmount(amount);
    }

    public void setAccountWithdraw(int accountId, int amount) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account " + accountId + " not found.");
        }
        Account account = accounts.get(accountId);
        if (!hasLimitWithDraw(account, amount)) {
            throw new IllegalStateException("Account " + accountId + " limit has been reached.");
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    private int generateId() {
        return nextAccountId++;
    }

    private boolean hasAccountForUser(int userId) {
        return accounts.values().stream().anyMatch(account -> account.getUserId() == userId);
    }

    private boolean hasLimitWithDraw(Account account, int amount){
        return account.getMoneyAmount() >= amount;
    }
}