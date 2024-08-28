package com.min01.ironbound_mobestiary.entity.model;

import com.min01.ironbound_mobestiary.IronboundMobestiary;

import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobModel;
import net.minecraft.resources.ResourceLocation;

public class GamblerModel extends AbstractSpellCastingMobModel
{
    public static final ResourceLocation MODEL = new ResourceLocation(IronboundMobestiary.MODID, "geo/gambler.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/gambler.png");
    
    @Override
    public ResourceLocation getModelResource(AbstractSpellCastingMob arg0)
    {
        return MODEL;
    }
    
	@Override
	public ResourceLocation getTextureResource(AbstractSpellCastingMob arg0) 
	{
		return TEXTURE;
	}
}
