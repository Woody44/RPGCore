package com.rpg.klasy;

import java.sql.SQLException;
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

public class ChooseClass implements Listener
{
	private final Inventory inv;

    public ChooseClass()
    {
        inv = Bukkit.createInventory(null, 9, "Klasy");
        initializeItems();
    }

    public void initializeItems()
    {
    	inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "§lPalladyn"));
    	inv.addItem(createGuiItem(Material.DIAMOND_CHESTPLATE, "§lObronca"));
    	inv.addItem(createGuiItem(Material.BLAZE_ROD, "§lMag"));
    	inv.addItem(createGuiItem(Material.GOLDEN_SWORD, "§lZabojca"));
    	inv.addItem(createGuiItem(Material.BOW, "§lStrzelec"));
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
    	ItemStack palladyn = createGuiItem(Material.DIAMOND_SWORD, "§lPalladyn");
        if(e.getInventory().contains(palladyn))
        {
        	Player player = (Player) e.getWhoClicked();
        	e.setCancelled(true);
        	try
        	{
                Main.dbmg.SetPlayerClass(player.getUniqueId().toString(), 1111);
            } catch (SQLException a)
        	{
                a.printStackTrace();
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
