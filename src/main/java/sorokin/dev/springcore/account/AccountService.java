package sorokin.dev.springcore.account;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountProperties  accountProperties;
    private final Map<Integer, Account> accounts = new HashMap<>();
    private int nextAccountId = 1;

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    public Account createAccount(int userId) {
        int accountId = generateId();
        Account account = new Account(accountId, userId, accountProperties.getDefaultAmount());
        accounts.put(accountId, account);
        return account;
    }

    public void setAccountDeposit(int accountId, double amount) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account " + accountId + " not found.");
        }
        Account account = accounts.get(accountId);
        account.setMoneyAmount(account.getMoneyAmount() + amount);
    }

    public void setAccountWithdraw(int accountId, double amount) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account " + accountId + " not found.");
        }
        Account account = accounts.get(accountId);
        if (!hasLimitWithDraw(account, amount)) {
            throw new IllegalStateException("Account " + accountId + " limit has been reached.");
        }
        account.setMoneyAmount(account.getMoneyAmount() - amount);
    }

    public void transferMoney(int fromAccountId, int toAccountId, int amount) {
        double totalAmount;
        if (!accounts.containsKey(fromAccountId)) {
            throw new IllegalStateException("Sender account " + fromAccountId + " not found.");
        }
        if (!accounts.containsKey(toAccountId)) {
            throw new IllegalStateException("Receiver account " + toAccountId + " not found.");
        }
        if (!hasLimitWithDraw(accounts.get(fromAccountId), amount)) {
            throw new IllegalStateException("Sender account " + fromAccountId + " limit has been reached.");
        }
        Account fromAccount = accounts.get(fromAccountId);
        Account toAccount = accounts.get(toAccountId);
        if (fromAccount.getUserId() != toAccount.getUserId()) {
            double tax = amount * accountProperties.getTransferCommission();
            totalAmount = amount - tax;
        } else {
            totalAmount = amount;
        }

        setAccountWithdraw(fromAccountId, amount);
        setAccountDeposit(toAccountId, totalAmount);
    }

    public void closeAccount(int accountId) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalStateException("Account " + accountId + " not found.");
        }
        Account closableAccount = accounts.get(accountId);
        List<Account> accountList = accounts.values().stream().filter(acc -> acc.getUserId() == closableAccount.getUserId()).collect(Collectors.toList());
        if (accountList.size() == 1) {
            throw new IllegalStateException("Account " + accountId + " cannot be closed because it is the user's only account.");
        } else {
            accounts.remove(accountId);
            accountList.remove(closableAccount);
            Account account = accountList.stream().min(Comparator.comparingInt(Account::getId)).orElse(null);
            account.setMoneyAmount(account.getMoneyAmount() + closableAccount.getMoneyAmount());
            closableAccount.setMoneyAmount(0);
        }
    }

    private int generateId() {
        return nextAccountId++;
    }

    private boolean hasAccountForUser(int userId) {
        return accounts.values().stream().anyMatch(account -> account.getUserId() == userId);
    }

    private boolean hasLimitWithDraw(Account account, double amount){
        return account.getMoneyAmount() >= amount;
    }
}