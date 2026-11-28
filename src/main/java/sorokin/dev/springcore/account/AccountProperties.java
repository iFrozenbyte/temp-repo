package sorokin.dev.springcore.account;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    @Value("${account.default-amount:0}")
    private int defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    public int getDefaultAmount() {
        return defaultAmount;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    @PostConstruct
    void validateProperties(){
        if (defaultAmount < 0){
            throw new IllegalArgumentException("Default amount cannot be negative");
        }
        if (!Double.isFinite(transferCommission) || transferCommission < 0 || transferCommission > 1){
            throw new IllegalArgumentException("Transfer commission must be between 0 and 1");
        }

    }
}