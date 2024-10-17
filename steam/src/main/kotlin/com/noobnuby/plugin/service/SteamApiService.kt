package com.noobnuby.plugin.service

import com.noobnuby.plugin.data.SteamSaleData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

object SteamApiService {
	private val client = HttpClient(CIO) {
		install(ContentNegotiation) {
			gson()
		}
		BrowserUserAgent()
	}

	suspend fun getSales(): SteamSaleData {
		val url = "https://steamsale.windbell.co.kr/api/v1/sales/currents?keyword=&page=1&size=5"

		return client.get(url).body()
	}

	suspend fun getHotSales(): SteamSaleData {
		val url = "https://steamsale.windbell.co.kr/api/v1/sales/tops?page=1&size=5"

		return client.get(url).body()
	}
}