package pt.ul.fc.css.soccernow;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import pt.ul.fc.css.soccernow.util.DummyDataGenerator;

@SpringBootApplication
public class SoccerNowApplication {

    private final DummyDataGenerator dummyDataGenerator;

    public SoccerNowApplication(DummyDataGenerator dummyDataGenerator) {
        this.dummyDataGenerator = dummyDataGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(SoccerNowApplication.class, args);
    }

    @Bean
    @Profile("!test")
    @Transactional
    public CommandLineRunner demo() {
        //dummyDataGenerator.generateDummyData();
        return (args) -> {
        };
    }

}
