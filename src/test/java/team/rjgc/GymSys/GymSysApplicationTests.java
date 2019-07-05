package team.rjgc.GymSys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GymSysApplicationTests {

	@Test
	public void contextLoads() {
		LocalDateTime a = LocalDateTime.now();
		LocalDateTime b = a.plusMinutes(90);
		Duration duration = Duration.between(a,b);
		long hours = duration.toHours();
		long minutes = duration.toMinutes();//相差的分钟数
		System.out.println("过去"+hours+"小时");
		System.out.println("过去"+minutes+"分钟");
		System.out.println(minutes==90);
	}

}
