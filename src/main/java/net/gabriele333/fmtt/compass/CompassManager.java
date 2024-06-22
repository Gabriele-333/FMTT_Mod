package net.gabriele333.fmtt.compass;/*
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



  THIS CODE IS COPIED AND MODIFIED FROM  https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/hooks/CompassManager.java
 */





import net.gabriele333.fmtt.network.FMTTNetwork;
//import net.gabriele333.fmtt.network.serverbound.CompassRequestPacket;

import java.util.HashMap;
import java.util.Iterator;


public class CompassManager {

    public static final CompassManager INSTANCE = new CompassManager();
    private final HashMap<CompassRequest, CompassResult> requests = new HashMap<>();

    public void postResult(long attunement, int x, int y, int z, CompassResult result) {
        final CompassRequest r = new CompassRequest(attunement, x, y, z);
        this.requests.put(r, result);
    }

    public CompassResult getCompassDirection(long attunement, int x, int y, int z) {
        final long now = System.currentTimeMillis();

        final Iterator<CompassResult> i = this.requests.values().iterator();
        while (i.hasNext()) {
            final CompassResult res = i.next();
            final long diff = now - res.getTime();
            if (diff > 20000) {
                i.remove();
            }
        }

        final CompassRequest r = new CompassRequest(attunement, x, y, z);
        CompassResult res = this.requests.get(r);

        if (res == null) {
            res = new CompassResult(false, true, 0);
            this.requests.put(r, res);
            this.requestUpdate(r);
        } else if (now - res.getTime() > 1000 * 3 && !res.isRequested()) {
            res.setRequested(true);
            this.requestUpdate(r);
        }

        return res;
    }

    private void requestUpdate(CompassRequest r) {
        //FMTTNetwork.instance().sendToServer(new CompassRequestPacket(r.attunement, r.cx, r.cz, r.cdy));
    }

    private static class CompassRequest {

        private final int hash;
        private final long attunement;
        private final int cx;
        private final int cdy;
        private final int cz;

        public CompassRequest(long attunement, int x, int y, int z) {
            this.attunement = attunement;
            this.cx = x >> 4;
            this.cdy = y >> 5;
            this.cz = z >> 4;
            this.hash = ((Integer) this.cx).hashCode() ^ ((Integer) this.cdy).hashCode()
                    ^ ((Integer) this.cz).hashCode() ^ ((Long) attunement).hashCode();
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final CompassRequest other = (CompassRequest) obj;
            return this.attunement == other.attunement && this.cx == other.cx && this.cdy == other.cdy
                    && this.cz == other.cz;
        }
    }
}