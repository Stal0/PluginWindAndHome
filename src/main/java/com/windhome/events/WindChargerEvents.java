package com.windhome.events;

import com.windhome.Windhome;
import com.windhome.entities.WindChargerSpecial;
import com.windhome.items.WindChargerCreate;
import org.bukkit.*;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class WindChargerEvents implements Listener {

    private static Windhome plugin = null;

    public WindChargerEvents(Windhome plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public static void onWindCharger(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null) {
                if (isWindCharge(item)) {
                    Player player = event.getPlayer();
                }
            }
        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() == null)) {
            if (event.getEntity() instanceof WindCharge) {
                WindCharge windCharge = (WindCharge) event.getEntity();
                if (WindChargerSpecial.isWindCharge(windCharge)) {

                    Location loc = windCharge.getLocation();
                    World world = loc.getWorld();
                    event.setCancelled(true);

                    loc.setY(loc.getY() - 1);
                    world.playSound(loc, Sound.ENTITY_WIND_CHARGE_WIND_BURST, 5.0f, 1.0f);

                    world.spawnParticle(Particle.EXPLOSION_EMITTER, loc, 5);
                    System.out.println(loc.getX() + " " + loc.getY() + " " + loc.getZ());

                    double knockbackRadius = 5.0; // Raio do knockback
                    double knockbackStrength = 5.0; // Força do knockback

                    List<Entity> nearbyEntities = world.getEntities();
                    for (Entity entity : nearbyEntities) {
                        if (entity.getLocation().distance(loc) <= knockbackRadius) {
                            Vector direction = entity.getLocation().toVector().subtract(loc.toVector()).normalize();
                            entity.setVelocity(direction.multiply(knockbackStrength));
                        }
                    }
                }
            }
        } else {
            System.out.println("Está dando null");
        }
    }

    @EventHandler
    public static void onWindCharger1(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getEntity().getShooter();
            dispenser.getInventory().getItem(1);
        }
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (isWindCharge(itemStack)) {
                event.getEntity().remove();
                if (event.getEntity() instanceof WindCharge) {
                    WindChargerSpecial.spawnWindCharge(player, plugin);
                }
            }
        }
    }

    private static boolean isWindCharge(ItemStack itemStack) {
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.equals(WindChargerCreate.windChargeCustom.getItemMeta())) {
                return true;
            }
        }
        return false;
    }
}
