package com.min01.ironbound_mobestiary.entity.render;

import com.min01.ironbound_mobestiary.IronboundMobestiary;
import com.min01.ironbound_mobestiary.entity.model.GamblerModel;
import com.min01.ironbound_mobestiary.entity.model.ModelDice;
import com.mojang.blaze3d.vertex.PoseStack;

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
	
	public GamblerRenderer(Context renderManager) 
	{
		super(renderManager, new GamblerModel());
		this.diceModel = new ModelDice(renderManager.bakeLayer(ModelDice.LAYER_LOCATION));
	}
	
	@Override
	public void render(AbstractSpellCastingMob entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) 
	{
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		
		poseStack.pushPose();
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -3.0F - entity.getEyeHeight(), 0.0F);
		this.diceModel.setupAnim(entity, 0, 0, entity.tickCount + partialTick, 0, 0);
		this.diceModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(DICE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}
}
