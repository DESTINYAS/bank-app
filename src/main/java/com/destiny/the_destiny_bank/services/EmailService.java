package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
