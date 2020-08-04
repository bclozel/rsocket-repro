package com.example.rsocketrepro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootTest(properties = {"spring.rsocket.server.port=0",
		"spring.profiles.active=default"})
		//"spring.profiles.active=immediate"})
public class RsocketReproApplicationTests {

	@LocalRSocketServerPort
	private int port;

	@Autowired
	private RSocketRequester.Builder builder;

	@Test
	void rSocketEndpoint() {
		RSocketRequester requester = this.builder.tcp("localhost", this.port);
		Mono<Project> result = requester.route("find.project.spring-boot").retrieveMono(Project.class);
		StepVerifier.create(result)
				.assertNext((project) -> Assertions.assertThat(project.getName()).isEqualTo("spring-boot"))
				.verifyComplete();
	}

}
