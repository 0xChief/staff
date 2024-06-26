package io.ordex.ethescription.collections.utils

import io.daonomic.rpc.domain.Word
import io.ordex.ethescription.collections.model.EthscriptionCollectionItem

object EthscriptionItemValidator {
    fun validate(item: EthscriptionCollectionItem) {
        try {
            Word.apply(item.ethscriptionId)
        } catch (ex: Throwable) {
            throw IllegalArgumentException("Invalid item: id=${item.id}, ethscriptionId=${item.ethscriptionId}")
        }
    }

    fun isValid(item: EthscriptionCollectionItem): Boolean {
        return runCatching {
            validate(item)
            true
        }.getOrElse {
            false
        }
    }
}