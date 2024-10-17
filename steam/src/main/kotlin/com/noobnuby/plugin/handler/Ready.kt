package com.noobnuby.plugin.handler

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object Ready : ListenerAdapter() {
	@OptIn(DelicateCoroutinesApi::class)
	override fun onReady(ev: ReadyEvent) {
		GlobalScope.launch {
			println("Logged in as ${ev.jda.selfUser.asTag}")
		}
	}
}