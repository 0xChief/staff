package io.ordex.ethescription.collections.utils

import io.daonomic.rpc.domain.Binary
import io.ordex.ethescription.collections.model.EthscriptionCollectionItem
import io.ordex.ethescription.collections.model.EthscriptionCollectionMeta
import scalether.util.Hash

object CollectionHash {
    fun hashFromList(hashed: List<String>): String {
        val json = Mapper.MAPPER.writeValueAsBytes(hashed)
        return Binary.apply(Hash.sha3(json)).hex()
    }

    fun calculateHashes(collection: EthscriptionCollectionMeta): EthscriptionCollectionMeta {
        val collectionHash = getCollectionHash(collection)
        val itemHashes = getItemHashes(collection.collectionItems)
        return collection.copy(
            hash = collectionHash,
            collectionItems = collection.collectionItems.mapIndexed { index, item ->
                item.copy(hash = itemHashes[index])
            }
        )
    }

    private fun getItemHashes(items: List<EthscriptionCollectionItem>): List<String> {
        return items.map { item ->
            val noHashItem = item.copy(hash = null)
            val json = Mapper.MAPPER.writeValueAsBytes(noHashItem)
            Binary.apply(Hash.sha3(json)).hex()
        }
    }

    private fun getCollectionHash(collection: EthscriptionCollectionMeta): String {
        val noHashCollection = noHash(collection)
        val json = Mapper.MAPPER.writeValueAsBytes(noHashCollection)
        return Binary.apply(Hash.sha3(json)).hex()
    }

    private fun noHash(collection: EthscriptionCollectionMeta): EthscriptionCollectionMeta {
        val noHashItems = collection.collectionItems.map { it.copy(hash = null) }
        return collection.copy(collectionItems = noHashItems, hash = null)
    }
}