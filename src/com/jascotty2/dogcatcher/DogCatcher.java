/**
 * Programmer: Jacob Scott
 * Program Name: DogCatcher
 * Description:
 * Date: Apr 15, 2011
 */
package com.jascotty2.dogcatcher;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author jacob
 */
public class DogCatcher extends JavaPlugin {

    protected final static Logger logger = Logger.getLogger("Minecraft");
    public static final String name = "DogCatcher";
    //DCConfig config = new DCConfig(this);
    DCPlayerListener playerListener = new DCPlayerListener(this);

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Highest, this);

        pm.registerEvent(Event.Type.ENTITY_TARGET, new DCEntityListener(this), Event.Priority.Highest, this);

        Log("Version " + getDescription().getVersion() + " Enabled");
    }

    public void onDisable() {
        Log("Disabled");
    }

    public static void Log(String txt) {
        logger.log(Level.INFO, String.format("[%s] %s", name, txt));
    }

    public static void Log(Level loglevel, String txt) {
        Log(loglevel, txt, true);
    }

    public static void Log(Level loglevel, String txt, boolean sendReport) {
        logger.log(loglevel, String.format("[%s] %s", name, txt == null ? "" : txt));
    }

    public static void Log(Level loglevel, String txt, Exception params) {
        if (txt == null) {
            Log(loglevel, params);
        } else {
            logger.log(loglevel, String.format("[%s] %s", name, txt == null ? "" : txt), (Exception) params);
        }
    }

    public static void Log(Level loglevel, Exception err) {
        logger.log(loglevel, String.format("[%s] %s", name, err == null ? "? unknown exception ?" : err.getMessage()), err);
    }
} // end class DogCatcher

