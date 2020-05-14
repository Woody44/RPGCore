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

public class ChooseClassGUI implements Listener
{
	ItemStack palladyn = createGuiItem(Material.DIAMOND_SWORD, "�lPalladyn");
	ItemStack obronca = createGuiItem(Material.DIAMOND_CHESTPLATE, "�lObronca");
	ItemStack mag = createGuiItem(Material.BLAZE_ROD, "�lMag");
	ItemStack zabojca = createGuiItem(Material.GOLDEN_SWORD, "�lZabojca");
	ItemStack strzelec = createGuiItem(Material.BOW, "�lStrzelec");
	ItemStack reset = createGuiItem(Material.COAL, "�lReset klasy");
	private final Inventory inv;

    public ChooseClassGUI()
    {
        inv = Bukkit.createInventory(null, 9, "Klasy");
        initializeItems();
    }

    public void initializeItems()
    {
    	inv.setItem(0, palladyn);
    	inv.setItem(1, obronca);
    	inv.setItem(2, mag);
    	inv.setItem(3, zabojca);
    	inv.setItem(4, strzelec);
    	inv.setItem(8, reset);
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
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if(e.getView().getTitle().equalsIgnoreCase("Klasy"))
        {
        	e.setCancelled(true);
        	if(clickedItem.getType() == Material.DIAMOND_SWORD)
            {
        		int r = DatabaseManager.SetPlayerClass(player.getUniqueId().toString(), 1111);
                if(r != 0);
                    player.sendMessage("Aby zmienic klase, musisz ja zresetowac!");
            }
            else if(clickedItem.getType() == Material.DIAMOND_CHESTPLATE)
            {
            	int r = DatabaseManager.SetPlayerClass(player.getUniqueId().toString(), 2221);
                if(r != 0)
                	player.sendMessage("Aby zmienic klase, musisz ja zresetowac!");
            }
            else if(clickedItem.getType() == Material.BLAZE_ROD)
            {
            	int r = DatabaseManager.SetPlayerClass(player.getUniqueId().toString(), 3331);
                if(r != 0)
                	player.sendMessage("Aby zmienic klase, musisz ja zresetowac!");
            }
            else if(clickedItem.getType() == Material.GOLDEN_SWORD)
            {
            	int r = DatabaseManager.SetPlayerClass(player.getUniqueId().toString(), 4441);
                if(r != 0)
                	player.sendMessage("Aby zmienic klase, musisz ja zresetowac!");
            }
            else if(clickedItem.getType() == Material.BOW)
            {
            	int r = DatabaseManager.SetPlayerClass(player.getUniqueId().toString(), 5551);
                if( r != 0)
                    	player.sendMessage("Aby zmienic klase, musisz ja zresetowac!");
            }
            else if(clickedItem.getType() == Material.COAL)
            {
            	DatabaseManager.UpdatePlayerClass(player.getUniqueId().toString(), 0);
            }
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
