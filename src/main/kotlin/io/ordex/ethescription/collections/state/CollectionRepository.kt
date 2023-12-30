package io.ordex.ethescription.collections.state

import kotlinx.coroutines.flow.flow
import io.ordex.ethescription.collections.model.EthscriptionCollectionMeta
import io.ordex.ethescription.collections.model.RepositoryHash
import io.ordex.ethescription.collections.utils.Mapper
import java.nio.file.Paths

class CollectionRepository {
    private val collectionsPath = Paths.get("./collections")
    private val metaPath = Paths.get("./meta")
    private val mapper = Mapper.MAPPER

    fun save(collection: String, meta: EthscriptionCollectionMeta): EthscriptionCollectionMeta {
        val file = collectionsPath.resolve(fileName(collection))
        mapper.writeValue(file.toFile(), meta)
        return meta
    }

    fun save(meta: RepositoryHash) {
        val file = metaPath.resolve(fileName("repository-meta"))
        mapper.writeValue(file.toFile(), meta)
    }

    fun get(collection: String): EthscriptionCollectionMeta? {
        val raw = getRaw(collection) ?: return null
        return mapper.readValue(raw, EthscriptionCollectionMeta::class.java)
    }

    fun getAll() = flow  {
        collectionsPath.toFile().listFiles()?.forEach {
            val extension = it.extension
            if (extension == "json") {
                val collection = it.nameWithoutExtension
                get(collection)?.let { meta -> emit(meta) }
            }
        }
    }

    private fun getRaw(collection: String): ByteArray? {
        val file = collectionsPath.resolve(fileName(collection))
        if (!file.toFile().exists()) {
            return null
        }
        return file.toFile().readBytes()
    }

    private fun fileName(collection: String) = "$collection.json"
}