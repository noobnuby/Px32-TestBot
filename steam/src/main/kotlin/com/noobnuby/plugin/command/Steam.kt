package com.noobnuby.plugin.command

import com.noobnuby.plugin.data.ButtonState
import com.noobnuby.plugin.data.SteamSaleData
import com.noobnuby.plugin.handler.ButtonClick
import com.noobnuby.plugin.service.SteamApiService
import com.noobnuby.plugin.util.formatWon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.LayoutComponent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.requests.RestAction
import net.projecttl.p.x32.api.command.GlobalCommand
import net.projecttl.p.x32.api.command.useCommand
import net.projecttl.p.x32.api.util.footer
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

object Steam : GlobalCommand {
	private val buttonTime = mutableMapOf<InteractionHook, Int>()

	override val data = useCommand {
		name = "스팀"
		description = "스팀 관련 명령어 입니다."

		subcommand {
			name = "할인목록"
			description = "현재 할인하는 게임을 보여줍니다"
		}

		subcommand {
			name = "인기할인"
			description = "현재 인기있는 할인 게임을 보여줍니다."
		}
	}

	override suspend fun execute(ev: SlashCommandInteractionEvent) {
		val sale = runBlocking {
			SteamApiService.getSales().list
		}

		val hotSale = runBlocking {
			SteamApiService.getHotSales().list
		}

		when (ev.subcommandName) {
			"할인목록" -> {
				ev.replyEmbeds(steamSaleEmbed(sale, false, ev.user)).addActionRow(
					Button.secondary("previousSteamSale", "◀").asDisabled(),
					Button.secondary("nextSteamSale", "▶")
				).queue {
					it.deleteComponents().queueAfter(10, TimeUnit.SECONDS)
				}
			}

			"인기할인" -> {
				ev.replyEmbeds(steamSaleEmbed(hotSale, true, ev.user)).addActionRow(
					Button.secondary("previousSteamHotSale", "◀").asDisabled(),
					Button.secondary("nextSteamHotSale", "▶")
				).queue {
					it.deleteComponents().queueAfter(10, TimeUnit.SECONDS)
				}
			}
		}
	}
	private fun InteractionHook.deleteComponents() = editOriginalComponents()

	fun steamSaleEmbed(list: List<SteamSaleData>, hot: Boolean,user: User): MessageEmbed {
		val embed = EmbedBuilder()
		if (hot) embed.setTitle("스팀 인기 할인목록")
		else embed.setTitle("스팀 할인목록")

		list.forEach {
			val saleInfo = """
				~~￦${it.full_price_va.formatWon()}~~ → ￦${it.sale_price_va.formatWon()}
				[link](${it.store_lk})
			""".trimIndent()

			embed.addField("${it.title_nm} (${(it.discount_rt * 100).toInt()}%)", saleInfo, false)
		}
		embed.footer(user)

		return embed.build()
	}
}