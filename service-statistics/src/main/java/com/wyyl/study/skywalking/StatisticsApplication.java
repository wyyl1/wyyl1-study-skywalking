package com.wyyl.study.skywalking;

import com.wyyl.study.skywalking.statistics.rocket.RocketConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author aoe
 */
@SpringBootApplication
public class StatisticsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}

	@Resource
	private RocketConsumer rocketConsumer;

	@Override
	public void run(String... args) throws Exception {
		rocketConsumer.start();
	}
}
