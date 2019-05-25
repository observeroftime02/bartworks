/*
 * Copyright (c) 2019 bartimaeusnek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bartimaeusnek.crossmod.galacticraft.solarsystems;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class CalebriSolarSystem {

    public static SolarSystem CalebriSolarSystem;
    public static Star CalebriA;
    public static Planet[] CalebriX;

    static {
        CalebriSolarSystem = new SolarSystem("CalebriSolarSystem", "milkyWay").setMapPosition(new Vector3(1.0D, -0.5D, -1.0D));
        CalebriA = (Star) new Star("CalebriA").setParentSolarSystem(CalebriSolarSystem).setTierRequired(-1);
        CalebriSolarSystem.setMainStar(CalebriA);
        CalebriX = new Planet[13];
        for (int i = 0; i < 13; i++) {
            CalebriX[i] = (Planet) new Planet("Calebri"+i).setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(i*0.25F+0.25f, i*0.25F+0.25f));
        }

    };
}
