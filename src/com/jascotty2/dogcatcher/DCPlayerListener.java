/**
 * Programmer: Jacob Scott
 * Program Name: DCPlayerListener
 * Description:
 * Date: Apr 15, 2011
 */
package com.jascotty2.dogcatcher;

import com.jynxdaddy.wolfspawn_04.UpdatedWolf;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * @author jacob
 */
public class DCPlayerListener extends PlayerListener {

    final static double MAX_DIST = 4; // max dist can select something
    DogCatcher plugin = null;

    public DCPlayerListener(DogCatcher callbackPlugin) {
        plugin = callbackPlugin;
    } // end default constructor

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            // also occurs if right-click an entity..
            Wolf w = selectedWolf(event.getPlayer());
            if (w != null) {
                UpdatedWolf uw = new UpdatedWolf(w);
                if (!uw.isTame()) {
                    event.getPlayer().sendMessage("Wild Wolf" + String.format(" (health: %2.0f%%)", w.getHealth() / .08));
                } else {
                    event.getPlayer().sendMessage("This wolf belongs to " + uw.getOwner() + String.format(" (health: %2.0f%%)", w.getHealth() / .2));
                }
                event.setCancelled(true);
            }
        }
    }

    Wolf selectedWolf(Player pl) {
        List<Entity> ents = pl.getNearbyEntities(MAX_DIST, MAX_DIST, MAX_DIST);

        Location //orgin = pl.getLocation().clone(),
                rel = new Location(pl.getWorld(), 0, 0, 0, 0, 0);
        //orgin.setX(0);
        //orgin.setY(-1);
        //orgin.setZ(0);

        // get the direction the player is facing
        double rot = (pl.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360;
        }

        // now find if pointing to one of these entities
        for (Entity e : ents) {
            if (e instanceof Wolf) {
                rel.setX(e.getLocation().getX() - pl.getLocation().getX());
                rel.setY(e.getLocation().getY() - pl.getLocation().getY() - 1);//+ orgin.getY()
                rel.setZ(e.getLocation().getZ() - pl.getLocation().getZ());
                float angTo = (float) Math.toDegrees(Math.atan2(-rel.getZ(), -rel.getX()));
                float maxDY = Math.abs(angTo - (float) Math.toDegrees(Math.atan2(-rel.getZ() + .5, -rel.getX() - .5)));
                if (angTo < 0) {
                    angTo += 360;
                }
                rel.setYaw(angTo);
                float d = (float) Math.sqrt(rel.getZ() * rel.getZ() + rel.getX() * rel.getX());
                angTo = (float) Math.toDegrees(Math.atan2(rel.getY(), d));
                rel.setPitch(-angTo);

                // now see if orgin -> rel within 1 block (1/2 to each dir)
                d = (float) Math.sqrt(rel.getZ() * rel.getZ()
                        + rel.getX() * rel.getX()
                        + rel.getY() * rel.getY());

                float maxDP = angTo - (float) Math.toDegrees(Math.atan2(rel.getY() - .5, d));

                //System.out.println(String.format("X:%2.1f Y:%2.1f Z:%2.1f Yaw:%3.1f Pitch:%3.1f",
                //        rel.getX(), rel.getY(), rel.getZ(), rel.getYaw(), rel.getPitch()));
                //System.out.println(rot + " " + pl.getLocation().getPitch());
                //System.out.println((rel.getYaw() - rot) + " " + (rel.getPitch() - pl.getLocation().getPitch()));
                //System.out.println(d + " a:" + angTo);
                //System.out.println(maxDY + " " + maxDP);
                //System.out.println(Math.abs(rel.getYaw() - rot) + " " + Math.abs(rel.getPitch() - pl.getLocation().getPitch()));
                if (Math.abs(rel.getPitch() - pl.getLocation().getPitch()) < maxDP
                        && Math.abs(rel.getYaw() - rot) < maxDY) {
                    // wolf selected
                    return (Wolf) e;
                }
            }
        }
        return null;
    }
} // end class DCPlayerListener

