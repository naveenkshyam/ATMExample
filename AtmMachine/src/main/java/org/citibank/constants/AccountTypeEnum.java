package org.citibank.constants;

import lombok.Getter;

@Getter
public enum AccountTypeEnum {
    CHECKING(1),
    SAVINGS(2);

    private int accountType;

    AccountTypeEnum(int accountType) {
        this.accountType = accountType;
    }

}
