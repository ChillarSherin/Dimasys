package com.chillarcards.dimasys.data.model


/**
 * Created by Sherin on 25-06-2024.
 */

data class LandingHomeModel(
    val code: String,
    val status: String,
    val message: String,
    val details: HomeDetail
)

data class HomeDetail(
    val onboarded_distributors: Int,
    val onboarded_sales_team: Int,
    val onboarded_merchants: Int,
    val total_available_inventory: Int,
    val total_inventory_at_distributor_level: Int
)
