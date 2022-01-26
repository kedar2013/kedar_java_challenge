package com.db.awmd.challenge.service.impl;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountNotFoundException;
import com.db.awmd.challenge.exception.NotEnoughFundsException;
import com.db.awmd.challenge.service.TransferValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferValidatorImpl implements TransferValidator {

    /**
        Validating the accounts and fund
     */
    public void validate(final Account currAccountFrom, final Account currAccountTo, final Transfer transfer)
            throws AccountNotFoundException, NotEnoughFundsException{

        if (currAccountFrom == null){
            throw new AccountNotFoundException("Account: " + transfer.getAccountFromId() + " not found.");
        }

        if (currAccountTo == null) {
            throw new AccountNotFoundException("Account: " + transfer.getAccountToId() + " not found.");
        }

        if (!enoughFunds(currAccountFrom, transfer.getAmount())){
            throw new NotEnoughFundsException("Not enough funds on account: " + currAccountFrom.getAccountId());
        }
    }

    private boolean enoughFunds(final Account account, final BigDecimal amount) {
        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

}
