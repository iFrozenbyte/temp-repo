package sorokin.dev.springcore.user;

import org.springframework.stereotype.Service;
import sorokin.dev.springcore.account.Account;
import sorokin.dev.springcore.account.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private final AccountService accountService;
    private int nextUserId = 1;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<User> getUsers() {
        return users;
    }

    public User createUser(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("login can't be empty");
        }
        loginValidation(login);

        int userId = generateId();
        List<Account> accounts = new ArrayList<>();
        Account defaultAccount = accountService.createAccount(userId);
        accounts.add(defaultAccount);
        User user = new User(userId, login, accounts);
        users.add(user);

        return user;
    }

    public Account createAdditionalAccount(int userId) {
        boolean isUserExist = users.stream().anyMatch(u -> u.getId() == userId);
        if (!isUserExist) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        Account additionalAccount = accountService.createAccount(userId);
        User user = users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
        user.getAccountList().add(additionalAccount);

        return additionalAccount;
    }

    public void closeAccount(int accountId) {
        Account closebleAccount = accountService.closeAccount(accountId);
        users.stream().
                filter(user -> user.getId() == closebleAccount.getUserId())
                .findFirst()
                .ifPresent(user -> user.getAccountList().remove(closebleAccount));
    }

    private void loginValidation(String login) {
        boolean exists = users.stream().anyMatch(user -> user.getLogin().equals(login));
        if (exists) {
            throw new IllegalStateException("User with login " + login + " already exists");
        }
    }

    private int generateId() {
        return nextUserId++;
    }
}