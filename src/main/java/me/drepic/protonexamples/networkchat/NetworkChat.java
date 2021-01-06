package me.drepic.protonexamples.networkchat;

import me.drepic.proton.Proton;
import me.drepic.proton.ProtonManager;
import me.drepic.proton.message.MessageHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NetworkChat extends JavaPlugin implements Listener {

    ProtonManager manager;
    
    public void onEnable(){
        manager = Proton.getProtonManager();
        if(manager != null){

            manager.registerMessageHandlers(this, this);
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable(){}

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
