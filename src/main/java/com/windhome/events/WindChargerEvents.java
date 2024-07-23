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

    /* A partir do momento em que o evento de EntityExplode é lançado,
     a explosão padrão é substituída por uma explosão criada artificialmente,
      alterando sua força de explosão e partículas, com base na config.yml */

    @EventHandler
    public void onExplosionWindCharger(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getEntity();
            event.setCancelled(true);
            WindChangeFactory.AlterParticle(windCharge, plugin);
            WindChangeFactory.AlterExplosionType(windCharge, plugin);
        }
    }

    /* Quando o projétil de Wind Charge é lançado, ele tem sua velocidade modificada com base na configuração da config.yml */

    @EventHandler
    public void onLaunchWindCharger(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getEntity();
            WindChangeFactory.AlterSpeedWindCharge(windCharge, plugin);
        }
    }


}
