package io.github.dovehome.create.next.generation.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import io.github.dovehome.create.next.generation.data.HeatConditionEx;
import io.github.dovehome.create.next.generation.data.HeatLevelEx;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = HeatCondition.class, remap = false)
public abstract class MixinHeatCondition {

    @Mutable
    @Shadow
    @Final
    private static HeatCondition[] $VALUES;

    @Invoker("<init>")
    public static HeatCondition creative$generation$init(String name, int ordinal, int color) {
        throw new AssertionError();
    }


    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void inject_clinit(CallbackInfo ci) {
        HeatConditionEx.RAGE_HEATED = creative$generation$add("RAGE_HEATED", 0x5C93E8);
        HeatConditionEx.OVERLOADED = creative$generation$add("OVERLOADED", 0x5C93E8);
        HeatConditionEx.EXTERMINATED = creative$generation$add("EXTERMINATED", 0x5C93E8);
        HeatConditionEx.DRAGON_BREATHING = creative$generation$add("DRAGON_BREATHING", 0x5C93E8);
        HeatConditionEx.GHOST_HEATED = creative$generation$add("GHOST_HEATED", 0x5C93E8);
        HeatConditionEx.SMOOTH_HEATED = creative$generation$add("SMOOTH_HEATED", 0x5C93E8);
    }

    @Unique
    private static HeatCondition creative$generation$add(String key, int color) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        HeatCondition ret = creative$generation$init(key, ordinal, color);
        $VALUES[ordinal] = ret;
        return ret;
    }


    /**
     * @author WarmthDawn
     * @reason the heat level changed completely
     */
    @Overwrite
    public boolean testBlazeBurner(BlazeBurnerBlock.HeatLevel level) {
        HeatCondition thisObj = (HeatCondition) (Object) this;

        if (thisObj == HeatConditionEx.EXTERMINATED) {
            return level == HeatLevelEx.EXTERMINATE;
        } else if (thisObj == HeatConditionEx.OVERLOADED) {
            return level.isAtLeast(HeatLevelEx.OVERLOAD);
        } else if (thisObj == HeatConditionEx.RAGE_HEATED) {
            return level.isAtLeast(HeatLevelEx.RAGE);
        } else if (thisObj == HeatCondition.SUPERHEATED) {
            return level.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING);
        } else if (thisObj == HeatCondition.HEATED) {
            return level.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING);
        } else if (thisObj == HeatConditionEx.DRAGON_BREATHING) {
            return level == HeatLevelEx.DRAGON_BREATH;
        } else if (thisObj == HeatConditionEx.SMOOTH_HEATED) {
            return level == HeatLevelEx.SMOOTH_PERMANENT || level == HeatLevelEx.SMOOTH;
        } else if (thisObj == HeatConditionEx.GHOST_HEATED) {
            return level == HeatLevelEx.GHOST;
        }

        return false;
    }

    @Inject(method = "visualizeAsBlazeBurner", at = @At("HEAD"), cancellable = true)
    public void inject_visualizeAsBlazeBurner(CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        HeatCondition thisObj = (HeatCondition) (Object) this;
        if (thisObj == HeatConditionEx.RAGE_HEATED) {
            cir.setReturnValue(HeatLevelEx.RAGE);
        } else if (thisObj == HeatConditionEx.OVERLOADED) {
            cir.setReturnValue(HeatLevelEx.OVERLOAD);
        } else if (thisObj == HeatConditionEx.EXTERMINATED) {
            cir.setReturnValue(HeatLevelEx.EXTERMINATE);
        } else if (thisObj == HeatConditionEx.DRAGON_BREATHING) {
            cir.setReturnValue(HeatLevelEx.DRAGON_BREATH);
        } else if (thisObj == HeatConditionEx.GHOST_HEATED) {
            cir.setReturnValue(HeatLevelEx.GHOST);
        } else if (thisObj == HeatConditionEx.SMOOTH_HEATED) {
            cir.setReturnValue(HeatLevelEx.SMOOTH_PERMANENT);
        } else {
            // use default logic
            return;
        }
        cir.cancel();

    }
}