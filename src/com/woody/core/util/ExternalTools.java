package com.woody.core.util;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ExternalTools {
	
	public static boolean Chance(float chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues) 
	{
		if(chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues < 0)
			chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues = 0;
		else if (chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues > 100)
			chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues = 100;
	
		float chance = chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues / 100;
		float value = (float)Math.random();
		
		return value <= chance;
	}
	
	public static float toDegree(double angle) {
	    return (float) Math.toDegrees(angle);
	}
	
	public static Vector getVector(Entity entity) {
	    if (entity instanceof Player)
	        return ((Player) entity).getEyeLocation().toVector();
	    else
	        return entity.getLocation().toVector();
	}
	
	public static Vector getVector(Location loc) {
	        return loc.toVector();
	}
	
	public static Location LookAt(Entity a, Entity b) 
	{
		Vector direction = getVector(a).subtract(getVector(b)).normalize();
		double x = direction.getX();
	    double y = direction.getY();
	    double z = direction.getZ();
	    
	    Location toReturn = a.getLocation().clone();
	    toReturn.setYaw(180 - toDegree(Math.atan2(x, z)));
	    toReturn.setPitch(90 - toDegree(Math.acos(y)));
	    
	    return toReturn;
	}
	
	public static Location LookAt(Entity a, Location b) 
	{
		Vector direction = getVector(a).subtract(getVector(b)).normalize();
		double x = direction.getX();
	    double y = direction.getY();
	    double z = direction.getZ();
	    
	    Location toReturn = a.getLocation().clone();
	    toReturn.setYaw(180 - toDegree(Math.atan2(x, z)));
	    toReturn.setPitch(90 - toDegree(Math.acos(y)));
	    
	    return toReturn;
	}
	
	public static ArrayList<Location> getCircle(Location center, double radius, int amount)
    {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<Location>();
        for(int i = 0;i < amount; i++)
        {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }
	
	public static ArrayList<Location> getSphere(Location center, int amount)
	{
		ArrayList<Location> locations = new ArrayList<Location>();
		for (double i = 0; i <= Math.PI; i += Math.PI / amount) {
			   double radius = Math.sin(i);
			   double y = Math.cos(i);
			   for (double a = 0; a < Math.PI * 2; a+= Math.PI / amount) {
			      double x = Math.cos(a) * radius;
			      double z = Math.sin(a) * radius;
			      locations.add(center.add(x, y, z));
			      // display particle at 'location'.
			      locations.add(center.subtract(x, y, z));
			   }
			}
		
		return locations;
	}
}
