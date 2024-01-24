package io.ordex.ethescription.collections

import io.ordex.ethescription.collections.state.CollectionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.web.client.RestTemplate

fun main(args: Array<String>) = runBlocking<Unit> {
    val repository = CollectionRepository()

    listOf("mfpurrfect")
        .map { repository.get(it)!! }
        .map { it.collectionItems }
        .flatten()
        .chunked(100)
        .map { chunk ->
            chunk.map {
                async {
                    while (true) {
                        try {
                            RestTemplate().delete("https://api.ordex.ai/v0.1/items/ETHEREUM_ETHSCRIPTION:${it.ethscriptionId}/resetMeta")
                            println("Updated for ${it.ethscriptionId}")
                            break
                        } catch (ex: Throwable) {
                            println("Can't reset ${it.ethscriptionId}")
                        }
                    }
                }
            }.awaitAll()
        }.lastOrNull()
}

