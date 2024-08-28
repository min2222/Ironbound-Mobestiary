package com.min01.ironbound_mobestiary.misc;

import com.min01.ironbound_mobestiary.IronboundMobestiary;
import com.min01.ironbound_mobestiary.item.IronboundMobestiaryItems;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IronboundMobestiaryCreativeModeTabs 
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronboundMobestiary.MODID);

    public static final RegistryObject<CreativeModeTab> MOBESTIARY_MOBS = CREATIVE_MODE_TAB.register("mobestiary_mobs", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.ironbound_mobestiary.mobestiary_mobs"))
    		.icon(() -> new ItemStack(ItemRegistry.ARCHEVOKER_SPAWN_EGG.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			output.accept(IronboundMobestiaryItems.GAMBLER_SPAWN_EGG.get());
    		}).build());
}
