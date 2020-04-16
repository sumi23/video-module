package com.example.video;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.video.controller.VideoController;

@SpringBootTest
class VideoApplicationTests {
	
	@Autowired
	VideoController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
