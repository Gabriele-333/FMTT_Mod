package net.gabriele333.fmtt.network.serverbound;/*
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

 COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/core/network/serverbound/CompassRequestPacket.java
 */





import net.gabriele333.fmtt.compass.CompassResult;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.gabriele333.fmtt.network.clientbound.CompassResponsePacket;
import net.gabriele333.fmtt.server.services.FMTTCompassService;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;



public record CompassRequestPacket(long attunement,
                                   int cx,
                                   int cz,
                                   int cdy) implements ServerboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, CompassRequestPacket> STREAM_CODEC = StreamCodec.ofMember(
            CompassRequestPacket::write,
            CompassRequestPacket::decode);

    public static final Type<CompassRequestPacket> TYPE = CustomFMTTPayload.createType("compass_request");

    @Override
    public Type<CompassRequestPacket> type() {
        return TYPE;
    }

    public static CompassRequestPacket decode(RegistryFriendlyByteBuf stream) {
        var attunement = stream.readLong();
        var cx = stream.readInt();
        var cz = stream.readInt();
        var cdy = stream.readInt();
        return new CompassRequestPacket(attunement, cx, cz, cdy);
    }

    public void write(RegistryFriendlyByteBuf data) {
        data.writeLong(this.attunement);
        data.writeInt(this.cx);
        data.writeInt(this.cz);
        data.writeInt(this.cdy);
    }

    @Override
    public void handleOnServer(ServerPlayer player) {
        var pos = new ChunkPos(this.cx, this.cz);
        var result = FMTTCompassService.getDirection(player.serverLevel(), pos, 174);

        var responsePacket = new CompassResponsePacket(attunement, cx, cz, cdy, new CompassResult(
                result.hasResult(), result.spin(), result.radians()));
        player.connection.send(responsePacket);
    }
}
