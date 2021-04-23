package tk.blugon0921.motd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Main extends JavaPlugin implements Listener {

    public static String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Motd" + ChatColor.GRAY + "] ";
    public static String enable = prefix + ChatColor.AQUA + "플러그인이 활성화 되었습니다";
    public static String disable = prefix + ChatColor.AQUA + "플러그인이 비활성화 되었습니다";
    private static final String text1 = ChatColor.AQUA + "plugins/Motd에 들어가서 config.yml을 수정해주세요";

    public static FileConfiguration config;
    public static File file = new File("plugins/Motd/config.yml");

    @Override
    public void onEnable() {
        System.out.println(enable);
        Bukkit.getPluginManager().registerEvents(this, this);

        config = YamlConfiguration.loadConfiguration(file);
        try {
            if(!file.exists()) {
                config.set("Motd1", "&7[" + "&9Motd" + "&7] " + text1);
                config.set("Motd2", "&7[" + "&9Motd" + "&7] " + text1);
                config.set("apply", true);
            }
            config.save(file);
        } catch (Exception loadException) {
            loadException.printStackTrace();
        }
    }



    @Override
    public void onDisable() {
        System.out.println(disable);
    }


    @EventHandler
    public void SetMotd(ServerListPingEvent event) {
        if (config.getBoolean("apply")) {
            if (config.contains("Motd1")) {
                if (config.contains("Motd2")) {
                    String motd1 = config.getString("Motd1");
                    String motd2 = config.getString("Motd2");

                    motd1 = motd1.replace('&', '§');
                    motd2 = motd2.replace('&', '§');
                    motd1 = motd1.replace("{version}", Bukkit.getServer().getMinecraftVersion());
                    motd2 = motd2.replace("{version}", Bukkit.getServer().getMinecraftVersion());

                    SimpleDateFormat yearS = new SimpleDateFormat("yyyy");
                    Date yearD = new Date();
                    motd1 = motd1.replace("{years}", yearS.format(yearD));
                    motd2 = motd2.replace("{years}", yearS.format(yearD));


                    SimpleDateFormat monthS = new SimpleDateFormat("MM");
                    Date monthD = new Date();
                    motd1 = motd1.replace("{month}", monthS.format(monthD));
                    motd2 = motd2.replace("{month}", monthS.format(monthD));


                    SimpleDateFormat dayS = new SimpleDateFormat("dd");
                    Date dayD = new Date();
                    motd1 = motd1.replace("{day}", dayS.format(dayD));
                    motd1 = motd1.replace("{day}", dayS.format(dayD));
                    motd2 = motd2.replace("{day}", dayS.format(dayD));


                    event.setMotd(motd1 + "\n" + motd2);
                } else {
                    config.set("Motd2", "");
                }
            } else {
                config.set("Motd1", "");
            }
        }
    }
}