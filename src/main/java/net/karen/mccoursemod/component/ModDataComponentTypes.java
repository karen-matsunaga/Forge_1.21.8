package net.karen.mccoursemod.component;

import com.mojang.serialization.Codec;
import net.karen.mccoursemod.MccourseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MccourseMod.MOD_ID);

    // Coordinates custom tag -> X, Y, Z positions
    public static final RegistryObject<DataComponentType<BlockPos>> COORDINATES = register("coordinates",
            builder -> builder.persistent(BlockPos.CODEC));

    // Multiplier custom tag
    public static final RegistryObject<DataComponentType<Integer>> MULTIPLIER = register("multiplier",
            builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    // Item Stage custom tag
    public static final RegistryObject<DataComponentType<Boolean>> ITEM_STAGE = register("item_stage",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    // Registry all custom Data Component
    private static <T>RegistryObject<DataComponentType<T>> register(String name,
                                                                    UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    // Registry all custom Data Component on bus group event
    public static void register(BusGroup busGroup) { DATA_COMPONENT_TYPES.register(busGroup); }
}