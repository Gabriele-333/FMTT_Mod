package net.gabriele333.fmtt.network.clientbound;/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2024, Gabriele_333, All rights reserved.
 *
 * From Magic To Tech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * From Magic To Tech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with From Magic To Tech.  If not, see <http://www.gnu.org/licenses/lgpl>.

 COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/core/network/clientbound/CompassResponsePacket.java
 */





import net.gabriele333.fmtt.compass.CompassManager;
import net.gabriele333.fmtt.compass.CompassResult;
import net.gabriele333.fmtt.network.ClientboundPacket;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;



public record CompassResponsePacket(long attunement,
                                    int cx,
                                    int cz,
                                    int cdy,
                                    CompassResult cr) implements ClientboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, CompassResponsePacket> STREAM_CODEC = StreamCodec.ofMember(
            CompassResponsePacket::write,
            CompassResponsePacket::decode);

    public static final Type<CompassResponsePacket> TYPE = CustomFMTTPayload.createType("compass_response");

    @Override
    public Type<CompassResponsePacket> type() {
        return TYPE;
    }

    public static CompassResponsePacket decode(RegistryFriendlyByteBuf stream) {
        var attunement = stream.readLong();
        var cx = stream.readInt();
        var cz = stream.readInt();
        var cdy = stream.readInt();

        var cr = new CompassResult(stream.readBoolean(), stream.readBoolean(), stream.readDouble());
        return new CompassResponsePacket(attunement, cx, cz, cdy, cr);
    }

    public void write(RegistryFriendlyByteBuf data) {
        data.writeLong(attunement);
        data.writeInt(cx);
        data.writeInt(cz);
        data.writeInt(cdy);

        data.writeBoolean(cr.isValidResult());
        data.writeBoolean(cr.isSpin());
        data.writeDouble(cr.getRad());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleOnClient(Player player) {
        CompassManager.INSTANCE.postResult(this.attunement, this.cx << 4, this.cdy << 5, this.cz << 4, this.cr);
    }
}
