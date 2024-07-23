package com.windhome.events;

import com.windhome.Windhome;
import com.windhome.entities.WindChargerSpecial;
import com.windhome.items.WindChargerCreate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;


public class WindChargerEvents implements Listener {

    private static Windhome plugin = null;

    public WindChargerEvents(Windhome plugin) {
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onExplosionWindCharger(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getEntity();
            Windhome.broadcastMessage("Explosão de WindCharge detectada");
            float newYield = 50F;
            windCharge.setYield(newYield); // Define o tamanho da explosão
            Windhome.broadcastMessage("Tamanho da explosão definido para: " + newYield);
        }
    }


}
