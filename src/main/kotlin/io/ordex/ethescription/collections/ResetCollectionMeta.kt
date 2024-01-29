package io.ordex.ethescription.collections

import io.ordex.ethescription.collections.state.CollectionRepository
import io.ordex.ethescription.collections.utils.WebClientProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.web.reactive.function.client.awaitBody

fun main(args: Array<String>) = runBlocking<Unit> {
    val repository = CollectionRepository()
    val rest = WebClientProvider.initTransport()

    listOf("thedarwins")
        .map { repository.get(it)!! }
        .map { it.collectionItems }
        .flatten()
        .chunked(100)
        .map { chunk ->
            chunk.map {
                async {
                    while (true) {
                        try {
                            rest
                                .delete()
                                .uri("https://api.ordex.ai/v0.1/items/ETHEREUM_ETHSCRIPTION:${it.ethscriptionId}/resetMeta")
                                .retrieve()
                                .awaitBody<Unit>()
                            println("Updated for ${it.ethscriptionId}")
                            break
                        } catch (ex: Throwable) {
                            println("Can't reset ${it.ethscriptionId}, $ex")
                        }
                    }
                }
            }.awaitAll()
        }.lastOrNull()
}

