package com.min01.ironbound_mobestiary;

import com.min01.ironbound_mobestiary.entity.IronboundMobestiaryEntities;
import com.min01.ironbound_mobestiary.item.IronboundMobestiaryItems;
import com.min01.ironbound_mobestiary.misc.IronboundMobestiaryCreativeModeTabs;
import com.min01.ironbound_mobestiary.network.IronboundMobestiaryNetwork;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IronboundMobestiary.MODID)
public class IronboundMobestiary
{
    public static final String MODID = "ironbound_mobestiary";
    
	public IronboundMobestiary() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IronboundMobestiaryEntities.ENTITY_TYPES.register(bus);
		IronboundMobestiaryCreativeModeTabs.CREATIVE_MODE_TAB.register(bus);
		IronboundMobestiaryItems.ITEMS.register(bus);
		
		IronboundMobestiaryNetwork.registerMessages();
	}
}
