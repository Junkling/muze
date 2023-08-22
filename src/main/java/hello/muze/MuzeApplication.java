package hello.muze;


import hello.muze.web.config.JpaConfig;
import hello.muze.web.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, SecurityConfig.class})
@SpringBootApplication(scanBasePackages = "hello.muze.web.controller")
public class MuzeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuzeApplication.class, args);
	}

}
