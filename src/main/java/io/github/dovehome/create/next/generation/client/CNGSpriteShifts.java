package io.github.dovehome.create.next.generation.client;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import io.github.dovehome.create.next.generation.CreateNextGeneration;
import net.minecraft.resources.ResourceLocation;

public class CNGSpriteShifts {
    public static void init() {
    }

    private static SpriteShiftEntry get(ResourceLocation originalLocation, String targetLocation) {
        return SpriteShifter.get(originalLocation, new ResourceLocation(CreateNextGeneration.MODID, targetLocation));
    }

    private static SpriteShiftEntry getBurnerFlame(String targetLocation) {
        return get(Create.asResource("block/blaze_burner_flame"), targetLocation);
    }

    public static final SpriteShiftEntry RAGE_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_rage_scroll");
    public static final SpriteShiftEntry OVERLOAD_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_overload_scroll");
    public static final SpriteShiftEntry EXTERMINATE_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_exterminate_scroll");
    public static final SpriteShiftEntry DRAGON_BREATH_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_dragon_breath_scroll");
    public static final SpriteShiftEntry GHOST_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_ghost_scroll");
    public static final SpriteShiftEntry SMOOTH_BURNER_FLAME = getBurnerFlame("block/blaze_burner_flame_smooth_scroll");



}