package com.min01.ironbound_mobestiary.entity.renderer;

import com.min01.ironbound_mobestiary.IronboundMobestiary;
import com.min01.ironbound_mobestiary.entity.living.EntityGambler;
import com.min01.ironbound_mobestiary.entity.model.GamblerModel;
import com.min01.ironbound_mobestiary.entity.model.ModelDice;
import com.mojang.blaze3d.vertex.PoseStack;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GamblerRenderer extends AbstractSpellCastingMobRenderer
{
	private final ModelDice diceModel;
	private static final ResourceLocation DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice.png");
	private static final ResourceLocation BLOOD_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_blood.png");
	private static final ResourceLocation ELDRITCH_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_eldritch.png");
	private static final ResourceLocation ENDER_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_ender.png");
	private static final ResourceLocation EVOCATION_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_evocation.png");
	private static final ResourceLocation FIRE_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_fire.png");
	private static final ResourceLocation HOLY_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_holy.png");
	private static final ResourceLocation ICE_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_ice.png");
	private static final ResourceLocation LIGHTNING_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_lightning.png");
	private static final ResourceLocation NATURE_DICE_TEXTURE = new ResourceLocation(IronboundMobestiary.MODID, "textures/entity/dice_nature.png");
	
	public GamblerRenderer(Context renderManager) 
	{
		super(renderManager, new GamblerModel());
		this.diceModel = new ModelDice(renderManager.bakeLayer(ModelDice.LAYER_LOCATION));
	}
	
	@Override
	public void render(AbstractSpellCastingMob entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) 
	{
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		
		if(entity instanceof EntityGambler gambler)
		{
			poseStack.pushPose();
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			poseStack.translate(0.0F, -3.0F - entity.getEyeHeight(), 0.0F);
			this.diceModel.setupAnim(entity, 0, 0, entity.tickCount + partialTick, 0, 0);
			this.diceModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getDiceTexture(gambler.school))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			poseStack.popPose();
		}
	}
	
	public ResourceLocation getDiceTexture(SchoolType school)
	{
		if(school == SchoolRegistry.ICE.get())
		{
			return ICE_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.FIRE.get())
		{
			return FIRE_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.HOLY.get())
		{
			return HOLY_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.EVOCATION.get())
		{
			return EVOCATION_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.BLOOD.get())
		{
			return BLOOD_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.NATURE.get())
		{
			return NATURE_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.ENDER.get())
		{
			return ENDER_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.LIGHTNING.get())
		{
			return LIGHTNING_DICE_TEXTURE;
		}
		else if(school == SchoolRegistry.ELDRITCH.get())
		{
			return ELDRITCH_DICE_TEXTURE;
		}
		return DICE_TEXTURE;
	}
}
