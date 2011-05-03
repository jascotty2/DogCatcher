/**
 * Programmer: Jacob Scott
 * Program Name: DCEntityListener
 * Description:
 * Date: Apr 15, 2011
 */
package com.jascotty2.dogcatcher;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * @author jacob
 */
public class DCEntityListener extends EntityListener {

    DogCatcher plugin = null;

    public DCEntityListener(DogCatcher callbackPlugin) {
        plugin = callbackPlugin;
    }

    @Override
    public void onEntityTarget(EntityTargetEvent event) {
        //System.out.println(event);
    }
} // end class DCEntityListener

