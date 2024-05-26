package me.cerrato.w2m.spaceships;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpaceshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipsApplication.class, args);
	}

}
