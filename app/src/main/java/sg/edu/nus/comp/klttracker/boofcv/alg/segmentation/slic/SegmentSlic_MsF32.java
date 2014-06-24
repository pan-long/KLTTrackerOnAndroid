/*
 * Copyright (c) 2011-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sg.edu.nus.comp.klttracker.boofcv.alg.segmentation.slic;

import sg.edu.nus.comp.klttracker.boofcv.struct.ConnectRule;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageFloat32;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageType;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.MultiSpectral;

/**
 * Implementation of {@link boofcv.alg.segmentation.slic.SegmentSlic} for image of type
 * {@link boofcv.struct.image.MultiSpectral} of type {@link boofcv.struct.image.ImageFloat32}.
 *
 * @author Peter Abeles
 */
public class SegmentSlic_MsF32 extends SegmentSlic<MultiSpectral<ImageFloat32>> {
	public SegmentSlic_MsF32(int numberOfRegions, float m, int totalIterations,
							 ConnectRule connectRule, int numBands) {
		super(numberOfRegions, m , totalIterations, connectRule,ImageType.ms(numBands,ImageFloat32.class));
	}

	@Override
	public void setColor(float[] color, int x, int y) {
		final int numBands = input.getNumBands();
		for( int i = 0; i < numBands; i++ ) {
			color[i] = input.getBand(i).unsafe_get(x,y);
		}
	}

	@Override
	public void addColor(float[] color, int index, float weight) {
		final int numBands = input.getNumBands();
		for( int i = 0; i < numBands; i++ ) {
			color[i] += input.getBand(i).data[index]*weight;
		}
	}

	@Override
	public float colorDistance(float[] color, int index) {
		final int numBands = input.getNumBands();
		float total = 0;
		for( int i = 0; i < numBands; i++ ) {
			float diff = input.getBand(i).data[index] - color[i];
			total += diff*diff;
		}

		return total;
	}

	@Override
	public float getIntensity(int x, int y) {
		final int numBands = input.getNumBands();
		final int index = input.getIndex(x,y);
		float total = 0;
		for( int i = 0; i < numBands; i++ ) {
			total += input.getBand(i).data[index];
		}

		return total/numBands;
	}
}
