package com.min01.ironbound_mobestiary.network;

import java.util.function.Supplier;

import com.min01.ironbound_mobestiary.entity.living.EntityGambler;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class GamblerSchoolSyncPacket 
{
	private final int entityID;
	private final ResourceLocation schoolId;
	
	public GamblerSchoolSyncPacket(Entity entity, SchoolType school) 
	{
		this.entityID = entity.getId();
		this.schoolId = school.getId();
	}

	public GamblerSchoolSyncPacket(FriendlyByteBuf buf)
	{
		this.entityID = buf.readInt();
		this.schoolId = buf.readResourceLocation();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityID);
		buf.writeResourceLocation(this.schoolId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(GamblerSchoolSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Minecraft mc = Minecraft.getInstance();
				Entity entity = mc.level.getEntity(message.entityID);
				if(entity instanceof EntityGambler gambler) 
				{
					gambler.school = SchoolRegistry.REGISTRY.get().getValue(message.schoolId);
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
