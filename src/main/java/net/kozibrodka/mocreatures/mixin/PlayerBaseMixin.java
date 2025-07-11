package net.kozibrodka.mocreatures.mixin;

import net.kozibrodka.mocreatures.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;


@Mixin(PlayerEntity.class)
public abstract class PlayerBaseMixin extends LivingEntity {


    @Shadow public String name;

    public PlayerBaseMixin(World arg) {
        super(arg);
    }

    @Shadow protected boolean isPvpEnabled() {
        return false;
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;commandWolvesToAttack(Lnet/minecraft/entity/LivingEntity;Z)V"))
    private void injected(Entity i, int par2, CallbackInfoReturnable<Boolean> cir) {
        Object obj3 = i;
        this.alertBigCat((LivingEntity)obj3, false);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;commandWolvesToAttack(Lnet/minecraft/entity/LivingEntity;Z)V"))
    private void injected1(Entity par1, CallbackInfo ci) {
        this.alertBigCat((LivingEntity)par1, true);
    }

    public void alertBigCat(LivingEntity entityliving, boolean flag){
        if ((entityliving instanceof CreeperEntity) || (entityliving instanceof GhastEntity)) {
            return;
        }

        if (entityliving instanceof WolfEntity) {
            WolfEntity entitywolf = (WolfEntity)entityliving;
            if(entitywolf.isTamed() && name.equals(entitywolf.getOwnerName()))
            {
                return;
            }
        }

        if(entityliving instanceof EntityBigCat) {
            EntityBigCat tygrys = (EntityBigCat)entityliving;
            if (tygrys.getTamed() && name.equals(tygrys.getOwner())) {
                return;
            }
        }

        if ((entityliving instanceof EntityHorse && ((EntityHorse)entityliving).getTamed()) || (entityliving instanceof EntityDolphin && ((EntityDolphin)entityliving).getTamed()) || (entityliving instanceof EntityShark && ((EntityShark)entityliving).tamed) || (entityliving instanceof EntityKitty && ((EntityKitty)entityliving).kittystate > 2 )) {
            return;
        }


        if ((entityliving instanceof PlayerEntity) && !isPvpEnabled()) {
            return;
        }

        List list = world.collectEntitiesByClass(EntityBigCat.class, Box.createCached(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(16D, 4D, 16D));
        Iterator iterator = list.iterator();
        do {
            if (!iterator.hasNext()) {
                break;
            }
            Entity entity = (Entity)iterator.next();
            EntityBigCat entitybigcat = (EntityBigCat)entity;
            if (entitybigcat.getTamed() && entitybigcat.getAdult() && entitybigcat.getTarget() == null && name.equals(entitybigcat.getOwner()) && (!flag || !entitybigcat.getSitting())) {
                if (!(entityliving instanceof PlayerEntity && !entitybigcat.getProtect())) {
                    entitybigcat.wstanSzybko();
                    entitybigcat.ustawCel(entityliving);
                }
            }
        } while(true);
    }

}
