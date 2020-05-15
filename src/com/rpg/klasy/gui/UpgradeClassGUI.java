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
import com.rpg.core.economy.Wallet;

public class UpgradeClassGUI implements Listener
{
	private Inventory inv;
	private String uuid;

    public void initializeItems()
    {
    	String test = "" + DatabaseManager.GetPlayerClass(uuid);
    	if(Integer.parseInt(test.substring(3)) == 1)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(1)));
    	else if(Integer.parseInt(test.substring(3)) == 2)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(2)));
    	else if(Integer.parseInt(test.substring(3)) == 3)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(3)));
    	else if(Integer.parseInt(test.substring(3)) == 4)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(4)));
    	else if(Integer.parseInt(test.substring(3)) == 5)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(5)));
    	else if(Integer.parseInt(test.substring(3)) == 6)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "Koszt: " + DatabaseManager.GetClassUpgradePrice(6)));
    	else if(Integer.parseInt(test.substring(3)) == 7)
    		inv.setItem(4, ItemManager.createItemStack(Material.EXPERIENCE_BOTTLE, "§aUlepszenie klasy", "§4Masz Maksymalny poziom klasy!"));
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
        if(e.getView().getTitle() == "                   Ulepszanie klasy")
        {
        	if(e.getRawSlot() == 4)
        	{
        		Player player = (Player) e.getWhoClicked();
        		e.setCancelled(true);
	        	if(DatabaseManager.GetClassUpgradePrice(Integer.parseInt(("" +DatabaseManager.GetPlayerClass(player.getUniqueId().toString())).substring(3))) != 0)
	        	{
	        		int cena = (int) ItemManager.CheckLore(e.getCurrentItem(), "Koszt");
	        		Wallet w = DatabaseManager.GetPlayerWallet(player.getUniqueId().toString());
	        		if(w.Money > cena)
	        		{
	        			DatabaseManager.UpdatePlayerClass(player.getUniqueId().toString(), 1, true);
	        			w.AddMoney(cena * -1);
	                	player.closeInventory();
	                	player.sendMessage("§6Ulepszono klase!");
	        		}
	        		else
	        		{
	        			player.sendMessage("§6Nie masz tyle pieniêdzy!");
	        			player.closeInventory();
	        		}
	        	}
	        	else if(DatabaseManager.GetClassUpgradePrice(Integer.parseInt(("" +DatabaseManager.GetPlayerClass(player.getUniqueId().toString())).substring(3))) == 0)
	        	{
	            	player.closeInventory();
	        	}
        	}
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
