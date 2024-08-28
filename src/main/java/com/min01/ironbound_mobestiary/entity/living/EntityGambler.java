package com.min01.ironbound_mobestiary.entity.living;

import java.util.List;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardRecoverGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityGambler extends AbstractSpellCastingMob
{
	public static final List<SchoolType> ALL_SCHOOL = SchoolRegistry.REGISTRY.get().getValues().stream().toList();
	public SchoolType school = SchoolRegistry.ICE.get();
	
	public final WizardAttackGoal wizardAttackGoal = new WizardAttackGoal(this, 1.25F, 10, 20).setDrinksPotions();
	
	public EntityGambler(EntityType<? extends PathfinderMob> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.reassessWeaponGoal();
	}
	
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new WizardRecoverGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 20.0D)
    			.add(Attributes.MOVEMENT_SPEED, 0.25D);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
    {
    	this.reassessWeaponGoal();
    	return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
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
    	
    	if(this.tickCount % 100 == 0)
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
    		this.goalSelector.removeGoal(this.wizardAttackGoal);
           
    		if(this.school == SchoolRegistry.ICE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    					.setSpells(List.of(SpellRegistry.ICICLE_SPELL.get(), SpellRegistry.ICICLE_SPELL.get(), SpellRegistry.ICICLE_SPELL.get(), SpellRegistry.CONE_OF_COLD_SPELL.get()),
    					List.of(SpellRegistry.COUNTERSPELL_SPELL.get()),
    	                List.of(SpellRegistry.FROST_STEP_SPELL.get()),
    	                List.of())
    					.setSingleUseSpell(SpellRegistry.SUMMON_POLAR_BEAR_SPELL.get(), 80, 400, 3, 6));
    		}
    		else if(this.school == SchoolRegistry.FIRE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.FIREBOLT_SPELL.get(), SpellRegistry.FIREBOLT_SPELL.get(), SpellRegistry.FIREBOLT_SPELL.get(), SpellRegistry.FIRE_BREATH_SPELL.get(), SpellRegistry.BLAZE_STORM_SPELL.get()),
    	                List.of(),
    	                List.of(SpellRegistry.BURNING_DASH_SPELL.get()),
    	                List.of())
    	                .setSingleUseSpell(SpellRegistry.MAGMA_BOMB_SPELL.get(), 80, 200, 4, 6));
    		}
    		else if(this.school == SchoolRegistry.HOLY.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.WISP_SPELL.get(), SpellRegistry.GUIDING_BOLT_SPELL.get()),
    	                List.of(SpellRegistry.GUST_SPELL.get()),
    	                List.of(),
    	                List.of(SpellRegistry.HEAL_SPELL.get()))
    	                .setSpellQuality(0.3F, 0.5F));
    		}
    		else if(this.school == SchoolRegistry.EVOCATION.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.FANG_STRIKE_SPELL.get(), SpellRegistry.FIRECRACKER_SPELL.get()),
    	                List.of(SpellRegistry.FANG_WARD_SPELL.get(), SpellRegistry.SHIELD_SPELL.get()),
    	                List.of(),
    	                List.of())
    	                .setSpellQuality(.4F, .6F)
    	                .setSingleUseSpell(SpellRegistry.INVISIBILITY_SPELL.get(), 40, 80, 5, 5));
    		}
    		else if(this.school == SchoolRegistry.BLOOD.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.BLOOD_NEEDLES_SPELL.get(), SpellRegistry.BLOOD_NEEDLES_SPELL.get(), SpellRegistry.WITHER_SKULL_SPELL.get(), SpellRegistry.BLOOD_SLASH_SPELL.get()),
    	                List.of(SpellRegistry.RAY_OF_SIPHONING_SPELL.get()),
    	                List.of(),
    	                List.of()));
    		}
    		else if(this.school == SchoolRegistry.NATURE.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.EARTHQUAKE_SPELL.get(), SpellRegistry.EARTHQUAKE_SPELL.get(), SpellRegistry.ACID_ORB_SPELL.get(), SpellRegistry.POISON_BREATH_SPELL.get(), SpellRegistry.STOMP_SPELL.get(), SpellRegistry.POISON_ARROW_SPELL.get()),
    	                 List.of(SpellRegistry.ROOT_SPELL.get()),
    	                 List.of(),
    	                 List.of(SpellRegistry.OAKSKIN_SPELL.get(), SpellRegistry.STOMP_SPELL.get()))
    	                .setSingleUseSpell(SpellRegistry.FIREFLY_SWARM_SPELL.get(), 80, 200, 4, 6)
    	                .setSpellQuality(.25F, .60F));
    		}
    		else if(this.school == SchoolRegistry.ENDER.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.MAGIC_MISSILE_SPELL.get(), SpellRegistry.MAGIC_ARROW_SPELL.get()),
    	                List.of(SpellRegistry.STARFALL_SPELL.get()),
    	                List.of(SpellRegistry.TELEPORT_SPELL.get()),
    	                List.of(SpellRegistry.EVASION_SPELL.get())));
    		}
    		else if(this.school == SchoolRegistry.LIGHTNING.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.BALL_LIGHTNING_SPELL.get(), SpellRegistry.CHAIN_LIGHTNING_SPELL.get(), SpellRegistry.LIGHTNING_LANCE_SPELL.get()),
    	                List.of(SpellRegistry.LIGHTNING_BOLT_SPELL.get(), SpellRegistry.ELECTROCUTE_SPELL.get()),
    	                List.of(),
    	                List.of(SpellRegistry.ASCENSION_SPELL.get())));
    		}
    		else if(this.school == SchoolRegistry.ELDRITCH.get())
    		{
    			this.goalSelector.addGoal(4, this.wizardAttackGoal
    	                .setSpells(List.of(SpellRegistry.ABYSSAL_SHROUD_SPELL.get(), SpellRegistry.SCULK_TENTACLES_SPELL.get(), SpellRegistry.ELDRITCH_BLAST_SPELL.get()),
    	                List.of(),
    	                List.of(),
    	                List.of(SpellRegistry.PLANAR_SIGHT_SPELL.get())));
    		}
        }
    }
}
