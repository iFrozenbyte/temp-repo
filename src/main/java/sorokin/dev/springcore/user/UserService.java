package sorokin.dev.springcore.user;

import org.springframework.stereotype.Service;
import sorokin.dev.springcore.account.Account;
import sorokin.dev.springcore.account.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AccountService accountService;
    private int nextUserId = 1;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    private int generateId() {
        return nextUserId++;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public User createUser(String login) {
        List<Account> accounts = new ArrayList<>();
        int userId = generateId();
        Account account = accountService.createAccount(userId);
        accounts.add(account);
        User user = new User(userId, login, accounts);
        users.add(user);

        return user;
    }
}