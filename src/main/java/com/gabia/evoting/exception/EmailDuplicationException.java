package com.gabia.evoting.exception;

public class EmailDuplicationException extends RuntimeException {
    public EmailDuplicationException(String email) {
        super("이미 존재하는 사용자 계정입니다. " + email);
    }
}