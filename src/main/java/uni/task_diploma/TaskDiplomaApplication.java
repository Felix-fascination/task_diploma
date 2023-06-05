package uni.task_diploma;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication()
//Commented this so that schedule wouldn't refresh everytime
@EnableScheduling
public class TaskDiplomaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskDiplomaApplication.class, args);
    }

}
