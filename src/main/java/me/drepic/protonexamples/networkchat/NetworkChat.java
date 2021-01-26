package me.drepic.protonexamples.networkchat;

import me.drepic.proton.Proton;
import me.drepic.proton.ProtonManager;
import me.drepic.proton.message.MessageHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class NetworkChat extends JavaPlugin implements Listener {

    private ProtonManager manager;

    @Override
    public void onEnable(){
        RegisteredServiceProvider<ProtonManager> registration = getServer().getServicesManager().getRegistration(ProtonManager.class);
        if(registration != null) {
            manager = registration.getProvider();
            manager.registerMessageHandlers(this, this);
            getServer().getPluginManager().registerEvents(this, this);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        PlayerMessage message = new PlayerMessage(event.getPlayer().getName(), event.getMessage());
        manager.broadcast("networkchat", "chatMessage", message);
    }

    @MessageHandler(namespace = "networkchat", subject="chatMessage")
    public void onChatReceived(PlayerMessage message){
        getServer().broadcastMessage(String.format("<%s> %s", message.getPlayer(), message.getMessage()));
    }

    public class PlayerMessage {
        String player;
        String message;

        public PlayerMessage(String player, String message) {
            this.player = player;
            this.message = message;
        }

        public String getPlayer() {
            return player;
        }

        public String getMessage() {
            return message;
        }

    }

}
