package net.gabriele333.fmtt.client.render.crystal_item;

import appeng.thirdparty.fabric.MutableQuadView;
import appeng.thirdparty.fabric.RenderContext;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CrystalItemBakedModel implements IDynamicBakedModel {
    private static final Vector3f PIVOT = new Vector3f(0.5f, 1.0f, 0.5f);
    private final BakedModel maincube;
    private final BakedModel frame1;
    private final BakedModel frame2;

    public CrystalItemBakedModel(BakedModel maincube, BakedModel frame1, BakedModel frame2) {
        this.maincube = maincube;
        this.frame1 = frame1;
        this.frame2 = frame2;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side,
                                    RandomSource rand, ModelData data, RenderType renderType) {

        boolean hasEntity = data.get(CrystalItemModel.HAS_ENTITY_PROPERTY);
        float time = (System.currentTimeMillis() % 100000) / 50f;

        List<BakedQuad> quads = new ArrayList<>();

        if (!hasEntity) {
            quads.addAll(transformPart(maincube, createRotation(time, 0.6f, 0.02f, 0.02f, 0.021f),
                    state, side, rand, data, renderType));
        }

        quads.addAll(transformPart(frame1, createRotation(time, 0.82f, 0.03f, 0.03f, 0.03f),
                state, side, rand, data, renderType));
        quads.addAll(transformPart(frame2, createRotation(time, 0.78f, -0.02f, -0.02f, -0.02f),
                state, side, rand, data, renderType));

        return quads;
    }

    private Quaternionf createRotation(float time, float speed, float x, float y, float z) {
        return new Quaternionf()
                .rotateX(time * x * speed)
                .rotateY(time * y * speed)
                .rotateZ(time * z * speed);
    }

    private List<BakedQuad> transformPart(BakedModel part, Quaternionf rotation,
                                          @Nullable BlockState state, @Nullable Direction side,
                                          RandomSource rand, ModelData data, RenderType type) {
        List<BakedQuad> transformed = new ArrayList<>();

        for (BakedQuad quad : part.getQuads(state, side, rand, data, type)) {
            transformed.add(transformQuad(quad, rotation));
        }

        return transformed;
    }

    private BakedQuad transformQuad(BakedQuad quad, Quaternionf rotation) {
        int[] vertexData = quad.getVertices().clone();
        Vector3f pos = new Vector3f();

        for (int i = 0; i < 4; i++) {
            int offset = i * 8;

            // Estrai la posizione
            pos.set(Float.intBitsToFloat(vertexData[offset]),
                    Float.intBitsToFloat(vertexData[offset + 1]),
                    Float.intBitsToFloat(vertexData[offset + 2]));

            // Applica la rotazione
            pos.sub(PIVOT);
            pos.rotate(rotation);
            pos.add(PIVOT);

            // Riposiziona i dati
            vertexData[offset] = Float.floatToRawIntBits(pos.x());
            vertexData[offset + 1] = Float.floatToRawIntBits(pos.y());
            vertexData[offset + 2] = Float.floatToRawIntBits(pos.z());
        }

        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(),
                quad.getSprite(), quad.isShade());
    }

    @Override public boolean useAmbientOcclusion() { return maincube.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return true; }
    @Override public boolean usesBlockLight() { return false; }
    @Override public boolean isCustomRenderer() { return false; }
    @Override public TextureAtlasSprite getParticleIcon() { return maincube.getParticleIcon(); }
    @Override public ItemTransforms getTransforms() { return maincube.getTransforms(); }
    @Override public ItemOverrides getOverrides() { return ItemOverrides.EMPTY; }
}