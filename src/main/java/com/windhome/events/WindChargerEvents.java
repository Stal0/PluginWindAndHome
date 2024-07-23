package com.windhome.events;

import com.windhome.Windhome;
import com.windhome.services.WindChangeFactory;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.Collection;


public class WindChargerEvents implements Listener {

    private static Windhome plugin = null;

    public WindChargerEvents(Windhome plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onExplosionWindCharger(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getEntity();
            Windhome.broadcastMessage("Explosão de WindCharge detectada");

            event.setCancelled(true);

            WindChangeFactory.AlterExplosionType(windCharge, plugin);
        }
    }
    @EventHandler
    public void onLaunchWindCharger(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getEntity();
            windCharge.setAcceleration(windCharge.getDirection().multiply(3));
            System.out.println("Acelerando.");
        }
    }


}
