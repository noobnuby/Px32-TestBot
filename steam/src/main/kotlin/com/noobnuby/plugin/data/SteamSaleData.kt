package com.noobnuby.plugin.data

import kotlinx.serialization.Serializable

@Serializable
data class SteamSaleData(
	val game_id: String,
	val sale_no: Int,
	val store_lk: String,
	val img_lk: String,
	val title_nm: String,
	val discount_rt: Double,
	val full_price_va: Int,
	val sale_price_va: Int,
	val start_dt: String,
	val end_dt: String,
	val total_cn: Int,
	val list: List<SteamSaleData>
)