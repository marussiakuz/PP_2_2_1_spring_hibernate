package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

   public static void main(String[] args) {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car nissan = new Car("Nissan", 327463875);
      Car bmw = new Car("BMW", 276854736);
      Car tesla = new Car("Tesla", 835767843);
      Car skoda = new Car("Skoda", 874365478);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru", nissan));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", bmw));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", tesla));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", skoda));

      userService.listUsers().forEach(System.out::println);

      System.out.printf("\nUser having car Tesla: %s\n\n", userService.findUserByCarModelAndSeries("TESLA",
              835767843));

      System.out.printf("\nUser having car Skoda: %s\n", userService.findUserByCarModelAndSeries("skoda",
              874365478));

      context.close();
   }
}
