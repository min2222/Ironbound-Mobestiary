package com.min01.ironbound_mobestiary.item;

import java.util.function.Supplier;

import com.min01.ironbound_mobestiary.IronboundMobestiary;
import com.min01.ironbound_mobestiary.entity.IronboundMobestiaryEntities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IronboundMobestiaryItems 
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IronboundMobestiary.MODID);
	
	public static final RegistryObject<Item> GAMBLER_SPAWN_EGG = registerSpawnEgg("gambler_spawn_egg", () -> IronboundMobestiaryEntities.GAMBLER.get(), 0, 0);
	
	public static <T extends Mob> RegistryObject<Item> registerSpawnEgg(String name, Supplier<EntityType<T>> entity, int color1, int color2) 
	{
		return ITEMS.register(name, () -> new ForgeSpawnEggItem(entity, color1, color2, new Item.Properties()));
	}
}
