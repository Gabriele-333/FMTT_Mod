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





 THIS CODE IS COPIED AND MODIFIED FROM https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/hooks/CompassResult.java
 */


public class CompassResult {

    private final boolean hasResult;
    private final boolean spin;
    private final double rad;
    private final long time;
    private boolean requested = false;

    public CompassResult(boolean hasResult, boolean spin, double rad) {
        this.hasResult = hasResult;
        this.spin = spin;
        this.rad = rad;
        this.time = System.currentTimeMillis();
    }

    public boolean isValidResult() {
        return this.hasResult;
    }

    public boolean isSpin() {
        return this.spin;
    }

    public double getRad() {
        return this.rad;
    }

    boolean isRequested() {
        return this.requested;
    }

    void setRequested(boolean requested) {
        this.requested = requested;
    }

    long getTime() {
        return this.time;
    }
}