package pl.gromada.youtube_api_mongo_db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:variables.properties")
@SpringBootApplication
public class YoutubeApiMongoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeApiMongoDbApplication.class, args);
    }

}
