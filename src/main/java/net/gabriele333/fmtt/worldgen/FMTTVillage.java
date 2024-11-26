package net.gabriele333.fmtt.worldgen;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.gabriele333.fmtt.mixins.StructureTemplatePoolAccessor;
import net.gabriele333.fmtt.fmtt;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.core.registries.Registries.TEMPLATE_POOL;
import static net.minecraft.core.registries.Registries.PROCESSOR_LIST;

@EventBusSubscriber(modid = fmtt.MOD_ID)
public class FMTTVillage {
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath("minecraft", "empty"));

    private static void addBuildingToPool(RegistryAccess registryAccess, String villagePiece, String nbtPieceRL, int weight) {
        // Access processor list
        Holder.Reference<StructureProcessorList> emptyProcessorList = registryAccess.registryOrThrow(PROCESSOR_LIST).getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        // Create the structure piece
        LegacySinglePoolElement piece = StructurePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        // Access the template pool
        ResourceLocation poolRL = ResourceLocation.tryParse(villagePiece);
        StructureTemplatePool pool = registryAccess.registryOrThrow(TEMPLATE_POOL).getOptional(poolRL).orElse(null);
        if (pool != null) {
            // Use Accessor to modify templates and rawTemplates
            StructureTemplatePoolAccessor poolAccessor = (StructureTemplatePoolAccessor) pool;

            // Modify the templates list
            ObjectArrayList<StructurePoolElement> templates = poolAccessor.getTemplates();
            for (int i = 0; i < weight; i++) {
                templates.add(piece);
            }
            poolAccessor.setTemplates(templates);

            // Modify the rawTemplates list
            List<Pair<StructurePoolElement, Integer>> rawTemplates = new ArrayList<>(poolAccessor.getRawTemplates());
            rawTemplates.add(new Pair<>(piece, weight));
            poolAccessor.setRawTemplates(rawTemplates);
        }
    }

    @SubscribeEvent
    public static void addNewVillageBuilding(final ServerAboutToStartEvent event) {
        RegistryAccess registryAccess = event.getServer().registryAccess();
        addBuildingToPool(registryAccess, "village/taiga/houses", "fmtt:village/village1", 10);
    }
}
