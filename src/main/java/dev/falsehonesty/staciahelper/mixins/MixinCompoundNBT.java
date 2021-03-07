package dev.falsehonesty.staciahelper.mixins;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Mixin(CompoundNBT.class)
public abstract class MixinCompoundNBT {
    @Shadow @Final private Map<String, INBT> tagMap;

    @Shadow
    private static void writeEntry(String name, INBT data, DataOutput output) throws IOException { }

    /**
     * @author FalseHonesty
     * @reason Fix {@code Error encoding packet: java.util.ConcurrentModificationException: null} when iterating tagMap
     */
    @Overwrite
    public void write(DataOutput output) throws IOException {
        try {
            for (String s : this.tagMap.keySet()) {
                INBT inbt = this.tagMap.get(s);
                writeEntry(s, inbt, output);
            }
        } catch (ConcurrentModificationException e) {
            // Sometimes the NBT gets changed while we're writing. In this case,
            // we'll just send whatever data we've written so far and scrap the rest. Perhaps there is a better
            // alternative (like using a CHM?), but this seems preferred to disconnecting the player.
        }

        output.writeByte(0);
    }
}
