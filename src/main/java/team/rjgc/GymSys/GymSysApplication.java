package team.rjgc.GymSys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("team.rjgc.GymSys.mapper")
@EnableSwagger2
@EnableScheduling
public class GymSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymSysApplication.class, args);
	}

}
