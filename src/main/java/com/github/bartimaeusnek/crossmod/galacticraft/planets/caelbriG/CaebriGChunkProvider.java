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

package com.github.bartimaeusnek.crossmod.galacticraft.planets.caelbriG;

import com.github.bartimaeusnek.bartworks.util.Pair;
import com.github.bartimaeusnek.bartworks.util.noise.OpenSimplexNoise;
import gregtech.api.objects.XSTR;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class CaebriGChunkProvider implements IChunkProvider {
    private static final double FEATURE_SIZE = 8192;
    private OpenSimplexNoise noise;
    private XSTR rand;
    private World worldObj;
    private double[][] blockheights;

    public CaebriGChunkProvider(World world, long seed, boolean mapFeaturesEnabled) {
        rand = new XSTR(seed);
        noise = new OpenSimplexNoise(seed);
//        blockheights = makeBlockHeights(makeChunkHeights(16,16));
        worldObj = world;
    }

//    public double[][] makeChunkHeights(int xSize, int zSize){
//        double[][] ret = new double[xSize][zSize];
//        for (int y = 0; y < zSize; y++) {
//            for (int x = 0; x < xSize; x++) {
//                double value = noise.eval(x/16f, y /16f*4f);
//                value+=1;
//                ret[y][x] = value;
//            }
//        }
//        return ret;
//    }
//
//    public double[][] makeBlockHeights(double[][] ChunkHeights){
//        int xSize = ChunkHeights.length*16;
//        int zSize = ChunkHeights.length*16;
//        double[][] ret = new double[ChunkHeights.length*16][ChunkHeights.length*16];
//
//        for (int y = 0; y < zSize; y++) {
//            for (int x = 0; x < xSize; x++) {
//                ret[y][x] = (noise.eval(x/16f,y/16f) + 1) * ChunkHeights[y/16][x/16];
//            }
//        }
//        return ret;
//    }

    @Override
    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return true;
    }

//    public double getHeightForBlock(int x, int z, int cX, int cZ){
//        int rX = cX*(x+1);
//        int rZ = cZ*(z+1);
//        rX = Math.abs(rX % blockheights.length);
//        rZ = Math.abs(rZ % blockheights[rX].length);
//        return blockheights[rX][rZ];
//    }

    @Override
    public Chunk provideChunk(int cX, int cZ) {
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        OpenSimplexNoise chunknoise = new OpenSimplexNoise(rand.nextLong());
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double mod = (Math.tanh(noise.eval(cX/FEATURE_SIZE, cZ/FEATURE_SIZE)))+1f;
                double octave2 = (noise.eval(cX/FEATURE_SIZE * 2, cZ/FEATURE_SIZE * 2) +1f)/2;
                double octave3 = (noise.eval(cX/FEATURE_SIZE * 4, cZ/FEATURE_SIZE * 4) +1f)/4;
                double chunkmod = (chunknoise.eval(x/16f+cX, z/16f+cZ)/32f)+1f;
                int actualHeight = OpenSimplexNoise.fastFloor((chunkmod)/2f*(Math.round(Math.pow((mod+octave2+octave3)/2f,2.34f)* 32f) / 32f)*64f);
                for (int y = 0; y < 256; y++) {
                    if (actualHeight > y)
                        ablock[(x * 16 + z) * 256 + y] = Blocks.dirt;
                    else
                        ablock[(x * 16 + z) * 256 + y] = Blocks.air;
                }
            }
        }
        Arrays.fill(abyte,(byte)0);
        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, cX, cZ);
        ChunkCache.put(new Pair<>(cX, cZ),chunk);
        return chunk;
    }



    @Override
    public Chunk loadChunk(int p_73158_1_, int p_73158_2_) {
        return ChunkCache.get(new Pair<>(p_73158_1_,p_73158_2_)) == null ? provideChunk(p_73158_1_,p_73158_2_) : ChunkCache.get(new Pair<>(p_73158_1_,p_73158_2_)) ;
    }

    LinkedHashMap<Pair<Integer,Integer>,Chunk> ChunkCache = new LinkedHashMap();

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {

    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return null;
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
        return new LinkedList();
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {

    }

    @Override
    public void saveExtraData() {

    }
}
