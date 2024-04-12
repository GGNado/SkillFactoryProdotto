package pkg.skillfactoryspring;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkillFactorySpringApplication {

    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context = SpringApplication.run(SkillFactorySpringApplication.class, args);
    }
    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(SkillFactorySpringApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

    @Bean
    BasicTextEncryptor textEncryptor(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretEncriptionKeyBlaBla1234");
        return textEncryptor;
    }

}
