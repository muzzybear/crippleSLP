package net.muzzy.tinybear;

import java.net.InetAddress;
import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class crippleSLP extends JavaPlugin implements Listener {

	private static crippleSLP plugin;

	@Override
	public void onEnable() {
		plugin = this;
		plugin.getServer().getPluginManager().registerEvents(this, this);
		plugin.saveDefaultConfig();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String addr = player.getAddress().getAddress().getHostAddress();

		plugin.getConfig().set("cache." + addr + ".UUID", player.getUniqueId().toString());
		plugin.getConfig().set("cache." + addr + ".Name", player.getName());
		plugin.saveConfig();
	}

	@EventHandler
	public void onPing(ServerListPingEvent event) {
		String addr = event.getAddress().getHostAddress();

		if(plugin.getConfig().isSet("cache." + addr + ".Name")) {
			plugin.getServer().getConsoleSender().sendMessage("[SLP] Request from " + addr + " (" + plugin.getConfig().getString("cache." + addr + ".Name") + ")");
		} else {
			plugin.getServer().getConsoleSender().sendMessage("[SLP] Request from " + addr);
		}

		Iterator<Player> iterator = event.iterator();
		while (iterator.hasNext()) {
		Player player = iterator.next();
			iterator.remove();
		}
	}

}
