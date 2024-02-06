package io.ordex.ethescription.collections

import kotlinx.coroutines.runBlocking
import io.ordex.ethescription.collections.state.CollectionRepository
import io.ordex.ethescription.collections.utils.EthscriptionItemValidator

fun main() = runBlocking<Unit> {
    val repository = CollectionRepository()
    val collections = listOf(
        "cryptosquaries"
    )
    collections.forEach { collection ->
        val meta = repository.get(collection)!!
        val items = meta.collectionItems.filter {
            EthscriptionItemValidator.isValid(it)
        }
        repository.save(meta.slug, meta.copy(collectionItems = items))
    }
}