package com.min01.ironbound_mobestiary.event;

import com.min01.ironbound_mobestiary.IronboundMobestiary;
import com.min01.ironbound_mobestiary.entity.IronboundMobestiaryEntities;
import com.min01.ironbound_mobestiary.entity.living.EntityGambler;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IronboundMobestiary.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) 
    {
    	event.put(IronboundMobestiaryEntities.GAMBLER.get(), EntityGambler.createAttributes().build());
    }
}
