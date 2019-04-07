package carrot.demo.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
