package com.ilginbor.LibraryManagementSystem;

import org.springframework.boot.SpringApplication;

public class TestLibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(Starter::main).with(TestcontainersConfiguration.class).run(args);
	}

}
