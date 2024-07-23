package com.windhome.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.WindCharge;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.windhome.entities.WindChargerSpecial.METADATA_KEY;


public class WindChargerCreate {

    public static ItemStack windChargeCustom;

    public static void init() {
        CreateWindCharger();
    }

    public static void CreateWindCharger() {

        ItemStack itemStack = new ItemStack(Material.WIND_CHARGE, 1);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {

            itemMeta.setDisplayName("§6Wind Charger Special True");
            List<String> lore = new ArrayList<>();
            lore.add("§7Algo especial há nesse item");
            lore.add("§8Seja feliz!");
            itemMeta.setLore(lore);
            itemMeta.addEnchant(Enchantment.DENSITY, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);


            itemStack.setItemMeta(itemMeta);
            itemStack.getItemMeta().toString();
        }

         windChargeCustom = itemStack;
    }

    public static boolean isWindCharge(WindCharge windCharge) {
        return windCharge.hasMetadata(METADATA_KEY);
    }

}
