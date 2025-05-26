package com.ProConnect.users.domain;

import com.ProConnect.entity.AbstractEntity;
import com.ProConnect.util.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Getter
@Client
@NoArgsConstructor
public class VerificationCode  extends AbstractEntity {
    private String verificationCode;
    @Setter
    private boolean emailSent = false;
    @OneToOne
    private User user;

    public VerificationCode(User user) {
        this.user = user;
        this.verificationCode =RandomStringUtils.random(6, false, true);
    }
}
