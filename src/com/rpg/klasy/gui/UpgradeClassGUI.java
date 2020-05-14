package com.rpg.klasy.gui;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rpg.core.DatabaseManager;

public class UpgradeClassGUI implements Listener
{
	private final Inventory inv;

    public UpgradeClassGUI()
    {
        inv = Bukkit.createInventory(null, 9, "                   Ulepszanie klasy");
        initializeItems();
    }

    public void initializeItems()
    {
    	inv.setItem(4, createGuiItem(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInventory(final HumanEntity ent)
    {
        ent.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e)
    {
    	ItemStack test = createGuiItem(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy");
    	Player player = (Player) e.getWhoClicked();
        if(e.getInventory().contains(test))
        {
        	DatabaseManager.UpdatePlayerClass(player.getUniqueId().toString(), 1, true);
        	e.setCancelled(true);
        	player.closeInventory();
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e)
    {
        if (e.getInventory() == inv)
        {
        	e.setCancelled(true);
        }
    }
}
