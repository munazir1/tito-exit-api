package in.gw.TitoExitApi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TitoExitApi2Application {

	public static void main(String[] args) {
		SpringApplication.run(TitoExitApi2Application.class, args);
	}

}
