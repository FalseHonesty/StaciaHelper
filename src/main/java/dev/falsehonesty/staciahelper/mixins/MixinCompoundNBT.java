package dev.falsehonesty.staciahelper.mixins;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataOutput;
import java.io.IOException;
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
        Set<String> keySet = this.tagMap.keySet();
        String[] strings = keySet.toArray(new String[0]);

        for (String s : strings) {
            INBT inbt = this.tagMap.get(s);
            if (inbt == null)
                continue;
            writeEntry(s, inbt, output);
        }

        output.writeByte(0);
    }
}
