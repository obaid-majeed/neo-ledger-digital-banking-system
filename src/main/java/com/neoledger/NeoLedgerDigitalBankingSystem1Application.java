 package com.neoledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
public class NeoLedgerDigitalBankingSystem1Application {

    public static void main(String[] args) {
        SpringApplication.run(
                NeoLedgerDigitalBankingSystem1Application.class,
                args
        );
    }
}