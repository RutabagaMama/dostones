package com.rutabagamama.dostones.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

import com.rutabagamama.dostones.items.HomeStone;
import com.rutabagamama.dostones.items.ItemBase;
import com.rutabagamama.dostones.items.PortalStone;
import com.rutabagamama.dostones.items.GrowStone;

public class ModItems {
	
	 public static final List<Item> ITEMS = new ArrayList<Item>();
	 
	 public static final Item DOSTONE = new ItemBase("dostone");

	 public static final Item HOMESTONE = new HomeStone("homestone");
	 
	 public static final Item GROWSTONE = new GrowStone("growstone");
	 
	 public static final Item PORTALSTONE = new PortalStone("portalstone");
	 
}
