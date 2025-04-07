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
                                    RandomSource rand, ModelData extraData, RenderType renderType) {

        float time = (System.currentTimeMillis() % 100000) / 50f; // 50ms = 1 tick

        Quaternionf mainRotation = calculateRotation(time, 0.60f, 0.02f, 0.02f, 0.021f);
        Quaternionf frame1Rotation = calculateRotation(time, 0.82f, 0.03f, 0.03f, 0.03f);
        Quaternionf frame2Rotation = calculateRotation(time, 0.78f, -0.02f, -0.02f, -0.02f);



        List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(transformPart(maincube, mainRotation, state, side, rand, extraData, renderType));
        quads.addAll(transformPart(frame1, frame1Rotation, state, side, rand, extraData, renderType));
        quads.addAll(transformPart(frame2, frame2Rotation, state, side, rand, extraData, renderType));

        return quads;
    }

    private Quaternionf calculateRotation(float time, float speed, float xFactor, float yFactor, float zFactor) {
        return new Quaternionf()
                .rotateX(time * xFactor * speed)
                .rotateY(time * yFactor * speed)
                .rotateZ(time * zFactor * speed);
    }

    private List<BakedQuad> transformPart(BakedModel part, Quaternionf rotation,
                                          @Nullable BlockState state, @Nullable Direction side, RandomSource rand,
                                          ModelData data, RenderType type) {
        List<BakedQuad> transformed = new ArrayList<>();
        MutableQuadView quadView = MutableQuadView.getInstance();

        RenderContext.QuadTransform transform = quad -> {
            Vector3f pos = new Vector3f();
            for (int i = 0; i < 4; i++) {
                quad.copyPos(i, pos);

                pos.sub(PIVOT);
                pos.rotate(rotation);
                pos.add(PIVOT);


                quad.pos(i, pos);
            }
            return true;
        };

        for (BakedQuad quad : part.getQuads(state, side, rand, data, type)) {
            quadView.fromVanilla(quad, null);
            transform.transform(quadView);
            transformed.add(quadView.toBlockBakedQuad());
        }
        return transformed;
    }

    // Metodi standard obbligatori
    @Override public boolean useAmbientOcclusion() { return maincube.useAmbientOcclusion(); }
    @Override public boolean isGui3d() { return true; }
    @Override public boolean usesBlockLight() { return false; }
    @Override public boolean isCustomRenderer() { return false; }
    @Override public TextureAtlasSprite getParticleIcon() { return maincube.getParticleIcon(); }
    @Override public ItemTransforms getTransforms() { return maincube.getTransforms(); }
    @Override public ItemOverrides getOverrides() { return ItemOverrides.EMPTY; }
}