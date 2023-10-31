package org.august.dtomodel;

import java.util.List;

public class DefaultUserModel extends UserModel {
    public DefaultUserModel() {
        super();

        this.bank=List.of("privatbank");
        this.currency=List.of("USD");
        this.sumbols=2;
        this.notificationTime=0;
    }
}
