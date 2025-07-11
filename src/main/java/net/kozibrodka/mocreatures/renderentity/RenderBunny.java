package net.kozibrodka.mocreatures.renderentity;

import net.kozibrodka.mocreatures.entity.EntityBunny;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderBunny extends LivingEntityRenderer
{

    public RenderBunny(EntityModel modelbase, float f)
    {
        super(modelbase, f);
    }

    public void render(LivingEntity entityliving, double d, double d1, double d2,
                               float f, float f1)
    {
        EntityBunny entitybunny = (EntityBunny)entityliving;
        super.render(entitybunny, d, d1, d2, f, f1);
    }

    protected void rotBunny(EntityBunny entitybunny)
    {
        if(!entitybunny.onGround && entitybunny.vehicle == null)
        {
            if(entitybunny.velocityY > 0.5D)
            {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            } else
            if(entitybunny.velocityY < -0.5D)
            {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else
            {
                GL11.glRotatef((float)(entitybunny.velocityY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected float getHeadBob(LivingEntity entityliving, float f)
    {
        EntityBunny entitybunny = (EntityBunny)entityliving;
        if(!entitybunny.getAdult())
        {
            stretch(entitybunny);
        }
        return (float)entityliving.age + f;
    }

    protected void applyScale(LivingEntity entityliving, float f)
    {
        rotBunny((EntityBunny)entityliving);
    }

    protected void stretch(EntityBunny entitybunny)
    {
        float f = entitybunny.getAge();
        GL11.glScalef(f, f, f);
    }
}
