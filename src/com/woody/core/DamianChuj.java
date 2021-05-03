package com.woody.core;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
public class DamianChuj implements ConfigurationSerializable{
    
    public String dupa = "dupa";
    public ItemStack item;

    public DamianChuj(String dupa, ItemStack item)
    {
        this.dupa = dupa;
        this.item = item;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("dupa", dupa);
        m.put("item", item);
        return m;
    }
}
