package com.ProConnect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@Slf4j
public class ProConnectApplication {

	public static void main(String[] args) {
		// Load .env.dev file instead of .env
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.filename(".env.dev") // Explicitly specify the .env.dev file
				.load();

		// Set system properties from .env.dev
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		// Print out environment variables for debugging
//		System.out.println("DB_HOST: " + dotenv.get("DB_HOST"));
//		System.out.println("DB_PORT: " + dotenv.get("DB_PORT"));
//		System.out.println("DB_NAME: " + dotenv.get("DB_NAME"));
//		System.out.println("DB_USER: " + dotenv.get("DB_USER"));
//		System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));
//		System.out.println(dotenv.get("MAIL_HOST"));
//		System.out.println(dotenv.get("MAIL_PORT"));
//		System.out.println(dotenv.get("MAIL_USERNAME"));
//		System.out.println(dotenv.get("MAIL_PASSWORD"));
//		System.out.println(dotenv.get("MAIL_SMTP_AUTH"));
//		System.out.println(dotenv.get("MAIL_SMTP_STARTTLS_ENABLE"));



		// Run Spring Boot Application
		SpringApplication.run(ProConnectApplication.class, args);
	}

}
