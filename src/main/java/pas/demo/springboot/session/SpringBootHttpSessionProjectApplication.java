package pas.demo.springboot.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class SpringBootHttpSessionProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHttpSessionProjectApplication.class, args);
    }
}
