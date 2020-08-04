package com.example.rsocketrepro;

import io.rsocket.plugins.SocketAcceptorInterceptor;
import reactor.core.scheduler.Schedulers;

import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
public class RSocketConfig {

	@Bean
	@Profile("default")
	RSocketServerCustomizer subscribeOnBoundidElasticSocketAcceptor() {
		SocketAcceptorInterceptor mockAcceptor = (a) -> new SubscribeOnSocketAcceptor(a, Schedulers.boundedElastic());
		return (server) -> server.interceptors((registry) -> registry.forSocketAcceptor(mockAcceptor));
	}

	@Bean
	@Profile("immediate")
	RSocketServerCustomizer subscribeOnImmediateSocketAcceptor() {
		SocketAcceptorInterceptor mockAcceptor = (a) -> new SubscribeOnSocketAcceptor(a, Schedulers.immediate());
		return (server) -> server.interceptors((registry) -> registry.forSocketAcceptor(mockAcceptor));
	}
}
