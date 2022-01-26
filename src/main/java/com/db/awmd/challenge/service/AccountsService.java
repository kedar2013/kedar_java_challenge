package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.AccountUpdate;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.NotEnoughFundsException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class AccountsService {

    @Getter
    private final AccountsRepository accountsRepository;

    @Getter
    private final NotificationService notificationService;

    @Autowired
    private TransferValidator transferValidator;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, NotificationService notificationService) {
        this.accountsRepository = accountsRepository;
        this.notificationService = notificationService;
    }

    public void createAccount(Account account) {
        this.accountsRepository.createAccount(account);
    }

    public Account getAccount(String accountId) {
        return this.accountsRepository.getAccount(accountId);
    }

    /**
     * Functionality for a transfer of money between accounts
     */
    public void makeTransfer(Transfer transfer) throws AccountNotFoundException, NotEnoughFundsException {

        final Account accountFrom = accountsRepository.getAccount(transfer.getAccountFromId());
        final Account accountTo = accountsRepository.getAccount(transfer.getAccountToId());
        final BigDecimal amount = transfer.getAmount();

        transferValidator.validate(accountFrom, accountTo, transfer);

        boolean successful = accountsRepository.updateAccountsBatch(Arrays.asList(
                new AccountUpdate(accountFrom.getAccountId(), amount.negate()),
                new AccountUpdate(accountTo.getAccountId(), amount)
                ));

        if (successful){
            notificationService.notifyAboutTransfer(accountFrom, "Transferring: " + transfer.getAmount() +" amount to the account ID " + accountTo.getAccountId() + " is now complete .");
            notificationService.notifyAboutTransfer(accountTo, "The account ID + " + accountFrom.getAccountId() + " transferred " + transfer.getAmount() + " in your account.");
        }
    }

}
