package sorokin.dev.springcore.user;

import sorokin.dev.springcore.account.Account;

import java.util.List;

public class User {
    int id;                     // уникальный идентификатор
    String login;               // логин пользователя
    List<Account> accountList;

    public User(
            int id,
            String login,
            List<Account> accountList
    ) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}