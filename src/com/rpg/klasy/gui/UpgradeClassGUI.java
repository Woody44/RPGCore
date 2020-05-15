package com.rpg.klasy.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import com.rpg.core.DatabaseManager;
import com.rpg.core.ItemManager;

public class UpgradeClassGUI implements Listener
{
	private Inventory inv;
	private String uuid;

    public void initializeItems()
    {
    	String test = "" + DatabaseManager.GetPlayerClass(uuid);
    	if(Integer.parseInt(test.substring(3)) == 1)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: 500"));
    }

    public void openInventory(final HumanEntity ent)
    {
    	inv = Bukkit.createInventory(null, 9, "                   Ulepszanie klasy");
    	uuid = ent.getUniqueId().toString();
        initializeItems();
        ent.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e)
    {
    	Player player = (Player) e.getWhoClicked();
        if(e.getRawSlot() == 4)
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
