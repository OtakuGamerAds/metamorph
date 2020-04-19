package mchorse.metamorph.capabilities.render;

import mchorse.metamorph.api.morphs.AbstractMorph;
import mchorse.metamorph.client.EntityModelHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelRenderer implements IModelRenderer
{
    public EntitySelector selector;
    public AbstractMorph morph;
    public long selectorTime = -1;
    public int check;

    public static IModelRenderer get(Entity entity)
    {
        return entity.getCapability(ModelProvider.MODEL, null);
    }

    @Override
    public void update(EntityLivingBase target)
    {
        if (this.check == 10)
        {
            this.watchSelector(target);
            this.check = 0;
        }

        this.check++;

        if (this.selector != null && this.morph != null)
        {
            this.morph.update(target);
        }
    }

    /**
     * Watch selector 
     */
    protected void watchSelector(EntityLivingBase target)
    {
        if (this.selector != null && (this.selector.time > this.selectorTime || !this.selector.matches(target)))
        {
            this.selector = null;
            this.morph = null;
            this.selectorTime = -1;
        }

        if (this.selector == null)
        {
            for (EntitySelector selector : EntityModelHandler.selectors)
            {
                if (selector.matches(target))
                {
                    this.selector = selector;
                    this.selectorTime = selector.time;
                    this.morph = this.selector.morph == null ? null : this.selector.morph.copy(target.world.isRemote);

                    break;
                }
            }
        }
    }

    /**
     * Render the animator controller based on given entity
     */
    @Override
    public boolean render(EntityLivingBase entity, double x, double y, double z, float partialTicks)
    {
        boolean render = this.selector != null && this.morph != null;

        if (render)
        {
            this.morph.render(entity, x, y, z, 0, partialTicks);
        }

        return render;
    }
}