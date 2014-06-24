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

package sg.edu.nus.comp.klttracker.boofcv.alg.feature.detect.intensity.impl;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageFloat32;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSInt16;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSInt32;
/**
 * <p>
 * Implementation of {@link ImplSsdCornerBase} for {@link ImageSInt16}.
 * </p>
 * 
 * <p>
 * DO NOT MODIFY.  Code has been automatically generated by {@link GenerateImplSsdCorner}.
 * </p>
 *
 * @author Peter Abeles
 */
public abstract class ImplSsdCorner_S16 extends ImplSsdCornerBase<ImageSInt16,ImageSInt32> {

	// temporary storage for convolution along in the vertical axis.
	private int tempXX[] = new int[1];
	private int tempXY[] = new int[1];
	private int tempYY[] = new int[1];

	// defines the A matrix, from which the eigenvalues are computed
	protected int totalXX, totalYY, totalXY;

	public ImplSsdCorner_S16( int windowRadius) {
		super(windowRadius,ImageSInt32.class);
	}

	@Override
	public void setImageShape( int imageWidth, int imageHeight ) {
		super.setImageShape(imageWidth,imageHeight);

		if( tempXX.length < imageWidth ) {
			tempXX = new int[imageWidth];
			tempXY = new int[imageWidth];
			tempYY = new int[imageWidth];
		}
	}

/**
	 * Compute the derivative sum along the x-axis while taking advantage of duplicate
	 * calculations for each window.
	 */
	@Override
	protected void horizontal() {
		short[] dataX = derivX.data;
		short[] dataY = derivY.data;

		int[] hXX = horizXX.data;
		int[] hXY = horizXY.data;
		int[] hYY = horizYY.data;

		final int imgHeight = derivX.getHeight();
		final int imgWidth = derivX.getWidth();

		int windowWidth = radius * 2 + 1;

		int radp1 = radius + 1;

		for (int row = 0; row < imgHeight; row++) {

			int pix = row * imgWidth;
			int end = pix + windowWidth;

			int totalXX = 0;
			int totalXY = 0;
			int totalYY = 0;

			int indexX = derivX.startIndex + row * derivX.stride;
			int indexY = derivY.startIndex + row * derivY.stride;

			for (; pix < end; pix++) {
				short dx = dataX[indexX++];
				short dy = dataY[indexY++];

				totalXX += dx * dx;
				totalXY += dx * dy;
				totalYY += dy * dy;
			}

			hXX[pix - radp1] = totalXX;
			hXY[pix - radp1] = totalXY;
			hYY[pix - radp1] = totalYY;

			end = row * imgWidth + imgWidth;
			for (; pix < end; pix++, indexX++, indexY++) {

				short dx = dataX[indexX - windowWidth];
				short dy = dataY[indexY - windowWidth];

				// saving these multiplications in an array to avoid recalculating them made
				// the algorithm about 50% slower
				totalXX -= dx * dx;
				totalXY -= dx * dy;
				totalYY -= dy * dy;

				dx = dataX[indexX];
				dy = dataY[indexY];

				totalXX += dx * dx;
				totalXY += dx * dy;
				totalYY += dy * dy;

				hXX[pix - radius] = totalXX;
				hXY[pix - radius] = totalXY;
				hYY[pix - radius] = totalYY;
			}
		}
	}

	/**
	 * Compute the derivative sum along the y-axis while taking advantage of duplicate
	 * calculations for each window and avoiding cache misses. Then compute the eigen values
	 */
	@Override
	protected void vertical( ImageFloat32 intensity ) {
		int[] hXX = horizXX.data;
		int[] hXY = horizXY.data;
		int[] hYY = horizYY.data;
		final float[] inten = intensity.data;

		final int imgHeight = horizXX.getHeight();
		final int imgWidth = horizXX.getWidth();

		final int kernelWidth = radius * 2 + 1;

		final int startX = radius;
		final int endX = imgWidth - radius;

		final int backStep = kernelWidth * imgWidth;

		for (x = startX; x < endX; x++) {
			int srcIndex = x;
			int destIndex = imgWidth * radius + x;
			totalXX = totalXY = totalYY = 0;

			int indexEnd = srcIndex + imgWidth * kernelWidth;
			for (; srcIndex < indexEnd; srcIndex += imgWidth) {
				totalXX += hXX[srcIndex];
				totalXY += hXY[srcIndex];
				totalYY += hYY[srcIndex];
			}

			tempXX[x] = totalXX;
			tempXY[x] = totalXY;
			tempYY[x] = totalYY;

			y = radius;
			// compute the eigen values
			inten[destIndex] = computeIntensity();
			destIndex += imgWidth;
			y++;
		}

		// change the order it is processed in to reduce cache misses
		for (y = radius + 1; y < imgHeight - radius; y++) {
			int srcIndex = (y + radius) * imgWidth + startX;
			int destIndex = y * imgWidth + startX;

			for (x = startX; x < endX; x++, srcIndex++, destIndex++) {
				totalXX = tempXX[x] - hXX[srcIndex - backStep];
				tempXX[x] = totalXX += hXX[srcIndex];
				totalXY = tempXY[x] - hXY[srcIndex - backStep];
				tempXY[x] = totalXY += hXY[srcIndex];
				totalYY = tempYY[x] - hYY[srcIndex - backStep];
				tempYY[x] = totalYY += hYY[srcIndex];

				inten[destIndex] = computeIntensity();
			}
		}
	}
}
