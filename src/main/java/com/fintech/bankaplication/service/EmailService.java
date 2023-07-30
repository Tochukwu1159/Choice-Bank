package com.fintech.bankaplication.service;

import com.fintech.bankaplication.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
