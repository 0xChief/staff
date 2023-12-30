package io.ordex.ethescription.collections.utils

import io.daonomic.rpc.domain.Word
import io.ordex.ethescription.collections.model.EthscriptionCollectionItem

object EthscriptionItemValidator {
    fun validate(item: EthscriptionCollectionItem) {
        try {
            Word.apply(item.ethscriptionId)
        } catch (ex: Throwable) {
            throw IllegalArgumentException("Invalid item: ${item.ethscriptionId}")
        }
    }
}