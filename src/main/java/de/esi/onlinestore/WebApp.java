package de.esi.onlinestore;

import de.esi.onlinestore.domain.*;
import de.esi.onlinestore.domain.enumeration.Size;
import de.esi.onlinestore.repository.*;
import de.esi.onlinestore.domain.enumeration.OrderItemStatus;
import de.esi.onlinestore.domain.enumeration.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
@SpringBootApplication
public class WebApp {

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }

}*/


@SpringBootApplication
public class WebApp implements CommandLineRunner {








    public static void main(String[] args) {

        SpringApplication.run(WebApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}




