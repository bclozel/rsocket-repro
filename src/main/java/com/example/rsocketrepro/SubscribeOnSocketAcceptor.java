package com.example.rsocketrepro;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

public class SubscribeOnSocketAcceptor implements SocketAcceptor {

	private final SocketAcceptor delegate;

	private final Scheduler scheduler;

	SubscribeOnSocketAcceptor(SocketAcceptor delegate, Scheduler scheduler) {
		this.delegate = delegate;
		this.scheduler = scheduler;
	}

	@Override
	public Mono<RSocket> accept(ConnectionSetupPayload connectionSetupPayload, RSocket rSocket) {
		return this.delegate.accept(connectionSetupPayload, rSocket).subscribeOn(this.scheduler);
	}

}
