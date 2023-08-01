package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestExampleApplication.class,args);
        Controller controller = context.getBean("controller", Controller.class);
        System.out.println("Answer: " + controller.getAnswer());
    }
}
