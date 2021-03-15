package com.example.demo;

import com.example.demo.models.Car;
import com.example.demo.models.Client;
import com.example.demo.models.ComfortLevel;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ComfortLevelRepository;
import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        // Would also work with javafx-weaver-core only:
        // return new FxWeaver(applicationContext::getBean, applicationContext::close);
        return new SpringFxWeaver(applicationContext);
    }
}
