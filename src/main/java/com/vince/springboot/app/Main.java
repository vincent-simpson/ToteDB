package com.vince.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		String pw_hash = BCrypt.hashpw("test123", BCrypt.gensalt());
		System.out.println("hashed pw = " + pw_hash);

		SpringApplication.run(Main.class, args);
	}

}
