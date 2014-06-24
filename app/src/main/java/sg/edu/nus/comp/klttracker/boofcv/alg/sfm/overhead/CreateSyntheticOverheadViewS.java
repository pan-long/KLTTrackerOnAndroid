/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
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

package sg.edu.nus.comp.klttracker.boofcv.alg.sfm.overhead;

import sg.edu.nus.comp.klttracker.boofcv.alg.interpolate.InterpolatePixelS;
import sg.edu.nus.comp.klttracker.boofcv.core.image.FactoryGImageSingleBand;
import sg.edu.nus.comp.klttracker.boofcv.core.image.GImageSingleBand;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSingleBand;
import georegression.struct.point.Point2D_F32;

/**
 * Implementation of {@link CreateSyntheticOverheadView} for {@link ImageSingleBand}.
 *
 * @author Peter Abeles
 */
public class CreateSyntheticOverheadViewS<T extends ImageSingleBand> extends CreateSyntheticOverheadView<T>
{
	// computes interpolated pixel value
	private InterpolatePixelS<T> interp;

	// local variables
	private GImageSingleBand output;

	/**
	 * Constructor
	 *
	 * @param interp Interpolation used when sampling camera image
	 */
	public CreateSyntheticOverheadViewS(InterpolatePixelS<T> interp) {
		this.interp = interp;
	}
	/**
	 * Computes overhead view of input image.  All pixels in input image are assumed to be on the ground plane.
	 *
	 * @param input (Input) Camera image.
	 * @param output (Output) Image containing overhead view.
	 */
	public void process(T input, T output) {

		this.output = FactoryGImageSingleBand.wrap(output,this.output);
		interp.setImage(input);

		int indexMap = 0;
		for( int i = 0; i < output.height; i++ ) {
			int indexOut = output.startIndex + i*output.stride;
			for( int j = 0; j < output.width; j++ , indexOut++,indexMap++ ) {
				Point2D_F32 p = mapPixels[indexMap];
				if( p != null ) {
					this.output.set(indexOut,interp.get( p.x, p.y));
				}
			}
		}
	}
}
