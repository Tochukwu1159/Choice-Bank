package com.fintech.bankaplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Choice Bank",
                description = "Backend Rest APIs for the Choice Bank",
                version = "v1.0",
                contact = @Contact(
                        name = "Udochukwu Tochukwu",
                        email="christinotochukwu@gmail.com",
                        url= "https://choicebank.com.ng"
                ),
                license = @License(
                        name = "Choice Bank",
                        url="license.com/choice_bank"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "The Choice Bank App Documentation",
                url = "https://choicebank.com.ng"
        )
)
public class BankAplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAplicationApplication.class, args);
    }

}
