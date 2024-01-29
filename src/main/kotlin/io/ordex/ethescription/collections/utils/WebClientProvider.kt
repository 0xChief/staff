package io.ordex.ethescription.collections.utils

import io.netty.channel.ChannelOption
import io.netty.channel.epoll.EpollChannelOption
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider

object WebClientProvider {
    fun initTransport(): WebClient {
        return WebClient.builder().run {
            clientConnector(clientConnector())
            build()
        }
    }

    private fun clientConnector(): ClientHttpConnector {
        val provider = ConnectionProvider.builder("webclient-connection-provider")
            .maxConnections(1000)
            .pendingAcquireMaxCount(-1)
            .lifo()
            .build()

        val client = HttpClient.create(provider)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .option(EpollChannelOption.TCP_KEEPCNT, 8)

        return ReactorClientHttpConnector(client)
    }
}