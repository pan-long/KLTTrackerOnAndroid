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

package sg.edu.nus.comp.klttracker.boofcv.alg.denoise.wavelet;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageFloat32;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSingleBand;
import org.ddogleg.sorting.QuickSelectArray;


/**
 * Various functions useful for denoising wavelet transforms.
 *
 * @author Peter Abeles
 */
public class UtilDenoiseWavelet {

	/**
	 * <p>
	 * Robust median estimator of the noise standard deviation.  Typically applied to the HH<sub>1</sub> subband.
	 * </p>
	 *
	 * <p>
	 * &sigma; = Median(|Y<sub>ij</sub>|)/0.6745<br>
	 * where &sigma; is the estimated noise standard deviation, and Median(|Y<sub>ij</sub>|)
	 * is the median absolute value of all the pixels in the subband.
	 * </p>
	 *
	 * <p>
	 * D. L. Donoho and I. M. Johnstone, "Ideal spatial adaption via wavelet shrinkage."
	 * Biometrika, vol 81, pp. 425-455, 1994
	 * </p>
	 *
	 * @param subband The subband the image is being computed from. Not modified.
	 * @param storage Used to temporarily store the absolute value of each element in the subband.
	 * @return estimated noise variance.
	 */
	public static float estimateNoiseStdDev( ImageFloat32 subband , float storage[] ) {

		storage = subbandAbsVal(subband, storage );

		int N = subband.width*subband.height;
		return QuickSelectArray.select(storage, N / 2, N)/0.6745f;
	}

	/**
	 * Computes the absolute value of each element in the subband image are places it into
	 * 'coef'
	 */
	public static float[] subbandAbsVal(ImageFloat32 subband, float[] coef ) {
		if( coef == null ) {
			coef = new float[subband.width*subband.height];
		}

		int i = 0;
		for( int y = 0; y < subband.height; y++ ) {
			int index = subband.startIndex + subband.stride*y;
			int end = index + subband.width;

			for( ;index < end; index++  ) {
				coef[i++] = Math.abs(subband.data[index]);
			}
		}
		return coef;
	}

	/**
	 * <p>
	 * Computes the universal threshold defined in [1], which is the threshold used by
	 * VisuShrink.  The same threshold is used by other algorithms.
	 * </p>
	 *
	 * <p>
	 * threshold = &sigma; sqrt( 2*log(max(w,h))<br>
	 * where (w,h) is the image's width and height.
	 * </p>
	 *
	 * <p>
	 * [1] D. L. Donoho and I. M. Johnstone, "Ideal spatial adaption via wavelet shrinkage."
	 * Biometrika, vol 81, pp. 425-455, 1994
	 * </p>
	 * @param image Input image.  Only the width and height are used in computing this thresold.
	 * @param noiseSigma Estimated noise sigma.
	 * @return universal threshold.
	 */
	public static double universalThreshold( ImageSingleBand image , double noiseSigma ) {
		int w = image.width;
		int h = image.height;

		return noiseSigma*Math.sqrt(2*Math.log(Math.max(w,h)));
	}
}
