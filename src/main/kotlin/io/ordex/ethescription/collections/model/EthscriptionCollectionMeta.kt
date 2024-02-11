package io.ordex.ethescription.collections.model

import com.fasterxml.jackson.annotation.JsonProperty

data class EthscriptionCollectionMeta(
    val name: String,

    @JsonProperty("logo_image_uri")
    val logoImageUri: String,

    @JsonProperty("banner_image_uri")
    val bannerImageUri: String,

    @JsonProperty("total_supply")
    val totalSupply: Int,

    val slug: String,

    val description: String,

    @JsonProperty("website_link")
    val websiteLink: String?,

    @JsonProperty("twitter_link")
    val twitterLink: String?,

    @JsonProperty("background_color")
    val backgroundColor: String?,

    @JsonProperty("collection_items")
    val collectionItems: List<EthscriptionCollectionItem>,

    val hash: String?,
)

data class EthscriptionCollectionItem(
    val id: Int,

    val name: String,

    @JsonProperty("ethscription_id")
    val ethscriptionId: String,

    @JsonProperty("item_attributes")
    val itemAttributes: List<EthscriptionItemAttribute>,

    val description: String?,

    val hash: String?,

    @JsonProperty("alternative_image_url")
    val alternativeImageUrl: String? = null,
)

data class EthscriptionItemAttribute(
    @JsonProperty("trait_type")
    val traitType: String?,

    @JsonProperty("value")
    val value: String
)
