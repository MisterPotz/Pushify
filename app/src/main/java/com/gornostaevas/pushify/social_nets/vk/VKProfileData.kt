package com.gornostaevas.pushify.social_nets.vk

// Возвращаемая информация по обществу
data class VKProfileData(
    val first_name : String,
    val last_name : String,
    val bdate : String,
    val bdate_visibility : Int,
    val city : IdTitle,
    val country : IdTitle,
    val home_town: String,
    val phone : String,
    val relation : Int,
    val sex : Int,
    val status: String
)

data class IdTitle(val id : Int, val title: String)