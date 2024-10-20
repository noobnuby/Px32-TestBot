package com.noobnuby.plugin.handler

import com.noobnuby.plugin.command.Steam
import com.noobnuby.plugin.data.ButtonState
import com.noobnuby.plugin.service.SteamApiService
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button

object ButtonClick : ListenerAdapter() {
	private val buttonState = mutableMapOf<String, ButtonState>()

	override fun onButtonInteraction(ev: ButtonInteractionEvent) {
		val buttonId = ev.componentId
		val currentState = buttonState.getOrDefault(ev.message.id, ButtonState(1, false))

		if (buttonId == "nextSteamSale" || buttonId == "nextSteamHotSale") {
			val isHotSale = buttonId == "nextSteamHotSale"

			buttonState[ev.message.id] = currentState.copy(page = currentState.page + 1)

			println("next " + buttonState[ev.message.id])

			val sale = runBlocking {
				if (isHotSale) {
					SteamApiService.getHotSales(buttonState[ev.message.id]!!.page).list
				} else {
					SteamApiService.getSales(buttonState[ev.message.id]!!.page).list
				}
			}
			val moreFl = runBlocking {
				if (isHotSale) {
					!SteamApiService.getHotSales(buttonState[ev.message.id]!!.page).more_fl
				} else {
					!SteamApiService.getSales(buttonState[ev.message.id]!!.page).more_fl
				}
			}

			ev.editMessageEmbeds(Steam.steamSaleEmbed(sale, isHotSale, ev.user)).setActionRow(
				Button.secondary(if(isHotSale) "previousSteamHotSale" else "previousSteamSale", "◀"),
				Button.secondary("nextSteamSale", "▶").withDisabled(moreFl)
			).queue()
		}

		if (buttonId == "previousSteamSale" || buttonId == "previousSteamHotSale") {
			val isHotSale = buttonId == "previousSteamHotSale"

			buttonState[ev.message.id] = currentState.copy(page = currentState.page - 1)

			println("previous " + buttonState[ev.message.id])

			val sale = runBlocking {
				if (isHotSale) {
					SteamApiService.getHotSales(buttonState[ev.message.id]!!.page).list
				} else {
					SteamApiService.getSales(buttonState[ev.message.id]!!.page).list
				}
			}
			val moreFl = runBlocking {
				if (isHotSale) {
					!SteamApiService.getHotSales(buttonState[ev.message.id]!!.page).more_fl
				} else {
					!SteamApiService.getSales(buttonState[ev.message.id]!!.page).more_fl
				}
			}

			ev.editMessageEmbeds(Steam.steamSaleEmbed(sale, isHotSale, ev.user)).setActionRow(
				Button.secondary(buttonId, "◀").withDisabled(buttonState[ev.message.id]!!.page <= 1),
				Button.secondary(if(isHotSale) "nextSteamHotSale" else "nextSteamSale", "▶").withDisabled(moreFl)
			).queue()
		}
	}
}
