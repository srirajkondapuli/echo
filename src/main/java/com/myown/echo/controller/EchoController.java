package com.myown.echo.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.myown.echo.model.Echo;



@RestController
public class EchoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/echo/{fromNS}/{toNS}")
	public Echo echoRemote(@PathVariable String fromNS, @PathVariable String toNS) {
		logger.info("echoRemote!!");
		logger.info("From NS: {}, To NS: {}", fromNS,toNS);

		String serviceUrl = String.format("http://echoservice.%s.svc.cluster.local:8000",toNS);
		WebClient webClient = WebClient.create(serviceUrl);

		Echo echo = webClient.get()
				.uri("/echo")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(Echo.class).block();

		logger.info("Echo Response: {}", echo);;


		return echo;

	}
	@GetMapping("/echo")
	public Echo echo() {
		return new Echo("Hello Echo !!!","","", LocalDateTime.now().toString());
	}
}
