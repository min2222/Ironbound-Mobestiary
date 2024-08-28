package com.min01.ironbound_mobestiary.entity.living;

import java.util.List;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardRecoverGoal;
import io.redspace.ironsspellbooks.entity.mobs.wizards.priest.PriestEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityGambler extends AbstractSpellCastingMob
{
	public static final List<SchoolType> ALL_SCHOOL = SchoolRegistry.REGISTRY.get().getValues().stream().toList();
	
	public SchoolType school = SchoolRegistry.ICE.get();
	
	public final WizardAttackGoal wizardAttackGoal = new WizardAttackGoal(this, 1.25F, 10, 20).setSpellQuality(1, 1).setDrinksPotions();
	
	public EntityGambler(EntityType<? extends PathfinderMob> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.reassessWeaponGoal();
	}
	
    @Override
    protected void registerGoals() 
    {
    	this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new WizardRecoverGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PriestEntity.class, true));
    }
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 90.0D)
    			.add(Attributes.ARMOR, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
    			.add(Attributes.MOVEMENT_SPEED, 0.25D);
    }
	
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) 
    {
    	return SoundEvents.EVOKER_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() 
    {
    	return SoundEvents.EVOKER_DEATH;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
    	return SoundEvents.EVOKER_AMBIENT;
    }
    
	@SuppressWarnings("deprecation")
	@Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
    {
    	this.reassessWeaponGoal();
    	return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }
    
    @Override
    protected boolean shouldDespawnInPeaceful()
    {
    	return true;
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag arg0) 
    {
    	super.readAdditionalSaveData(arg0);
    	this.reassessWeaponGoal();
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	
    	if(this.tickCount % 200 == 0)
    	{
        	this.gambleSchool();
    	}
    }
    
    public void gambleSchool()
    {
    	this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
		int random = (int) Math.floor(Math.random() * ALL_SCHOOL.size());
    	this.school = ALL_SCHOOL.get(random);
    	this.reassessWeaponGoal();
    }
    
    public void reassessWeaponGoal() 
    {
    	if(this.level != null && !this.level.isClientSide)
        {
    		List<AbstractSpell> allIceSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.ICE.get()).toList();
    		List<AbstractSpell> allFireSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.FIRE.get()).toList();
    		List<AbstractSpell> allHolySpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.HOLY.get()).toList();
    		List<AbstractSpell> allEvocationSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.EVOCATION.get()).toList();
    		List<AbstractSpell> allBloodSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.BLOOD.get()).toList();
    		List<AbstractSpell> allNatureSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.NATURE.get()).toList();
    		List<AbstractSpell> allEnderSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.ENDER.get()).toList();
    		List<AbstractSpell> allLightningSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.LIGHTNING.get()).toList();
    		List<AbstractSpell> allEldritchSpell = SpellRegistry.REGISTRY.get().getValues().stream().filter(t -> t.getSchoolType() == SchoolRegistry.ELDRITCH.get()).toList();
    		this.goalSelector.removeGoal(this.wizardAttackGoal);
    		if(this.school == SchoolRegistry.ICE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allIceSpell, allIceSpell, allIceSpell, allIceSpell));
    		}
    		else if(this.school == SchoolRegistry.FIRE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allFireSpell, allFireSpell, allFireSpell, allFireSpell));
    		}
    		else if(this.school == SchoolRegistry.HOLY.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allHolySpell, allHolySpell, allHolySpell, allHolySpell));
    		}
    		else if(this.school == SchoolRegistry.EVOCATION.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allEvocationSpell, allEvocationSpell, allEvocationSpell, allEvocationSpell));
    		}
    		else if(this.school == SchoolRegistry.BLOOD.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allBloodSpell, allBloodSpell, allBloodSpell, allBloodSpell));
    		}
    		else if(this.school == SchoolRegistry.NATURE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allNatureSpell, allNatureSpell, allNatureSpell, allNatureSpell));
    		}
    		else if(this.school == SchoolRegistry.ENDER.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allEnderSpell, allEnderSpell, allEnderSpell, allEnderSpell));
    		}
    		else if(this.school == SchoolRegistry.LIGHTNING.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allLightningSpell, allLightningSpell, allLightningSpell, allLightningSpell));
    		}
    		else if(this.school == SchoolRegistry.ELDRITCH.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal.setSpells(allEldritchSpell, allEldritchSpell, allEldritchSpell, allEldritchSpell));
    		}
        }
    }
}
