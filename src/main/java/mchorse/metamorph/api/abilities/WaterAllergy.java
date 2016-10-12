package mchorse.metamorph.api.abilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

/**
 * Water allergy ability
 * 
 * This ability is responsible for damaging the player when he is in the water 
 * primarily will be used in Enderman's morph.
 * 
 * This is more like a disability than an ability *Ba-dum-pam-dum-tsss*
 */
public class WaterAllergy extends Ability
{
    @Override
    public void update(EntityPlayer player)
    {
        if (player.isWet())
        {
            player.attackEntityFrom(DamageSource.drown, 1.0F);
        }
    }
}