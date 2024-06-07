package com.destiny.the_destiny_bank;

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
				title = "Java Bank Project",
				description = "Backend API documentation for my Bank app",
				version = "v1.0",
				contact = @Contact(
						name = "Destiny",
						email = "destiny.ochonogor@gmail.com",
						url = "https://github.com/DESTINYAS"
				),
				license = @License(
						name = "Destiny",
						url = "https://github.com/DESTINYAS/bank-app"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Destiny Bank App Documentation",
				url = "https://github.com/DESTINYAS/bank-app"
		)
)
public class TheDestinyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheDestinyBankApplication.class, args);
	}

}
