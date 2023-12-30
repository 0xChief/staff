package io.ordex.ethescription.collections

import io.ordex.ethescription.collections.model.RepositoryHash
import kotlinx.coroutines.runBlocking
import io.ordex.ethescription.collections.state.CollectionRepository
import io.ordex.ethescription.collections.utils.CollectionHash
import io.ordex.ethescription.collections.utils.EthscriptionItemValidator

fun main() = runBlocking<Unit> {
    val repository = CollectionRepository()
    val collectionHashes = mutableListOf<String>()

    repository.getAll().collect { collectionMeta ->
        println("name: ${collectionMeta.name}, items: ${collectionMeta.collectionItems.size}")
        collectionMeta.collectionItems.forEach { item ->
            EthscriptionItemValidator.validate(item)
        }
        val updatedCollection = CollectionHash.calculateHashes(collectionMeta)
        collectionHashes.add(updatedCollection.hash ?: error("Hash is null"))
        repository.save(collectionMeta.slug, updatedCollection)
    }
    val repositoryHash = CollectionHash.hashFromList(collectionHashes)
    repository.save(RepositoryHash(repositoryHash))
}