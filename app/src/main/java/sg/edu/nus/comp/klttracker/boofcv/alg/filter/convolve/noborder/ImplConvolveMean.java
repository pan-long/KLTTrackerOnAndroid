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
 */package sg.edu.nus.comp.klttracker.boofcv.alg.filter.convolve.noborder;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.*;

/**
 * <p>
 * Convolves a mean filter across the image.  The mean value of all the pixels are computed inside the kernel.
 * </p>
 * <p>
 * Do not modify.  Auto generated by {@link boofcv.alg.filter.convolve.noborder.GenerateImplConvolveMean}.
 * </p>
 * 
 * @author Peter Abeles
 */
public class ImplConvolveMean {

	public static void horizontal( ImageUInt8 input , ImageInt8 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final int divisor = kernelWidth;
		final int halfDivisor = divisor/2;

		for( int y = 0; y < input.height; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			int total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] & 0xFF;
			}
			output.data[indexOut++] = (byte)((total+halfDivisor)/divisor);

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] & 0xFF;
				total += input.data[ indexIn ] & 0xFF;

				output.data[indexOut++] = (byte)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void vertical( ImageUInt8 input , ImageInt8 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final int backStep = kernelWidth*input.stride;

		int divisor = kernelWidth;
		final int halfDivisor = divisor/2;
		int totals[] = new int[ input.width ];

		for( int x = 0; x < input.width; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			int total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] & 0xFF;
			}
			totals[x] = total;
			output.data[indexOut] = (byte)((total+halfDivisor)/divisor);
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride;
			int indexOut = output.startIndex + y*output.stride;

			for( int x = 0; x < input.width; x++ ,indexIn++,indexOut++) {
				int total = totals[ x ]  - (input.data[ indexIn - backStep ]& 0xFF);
				totals[ x ] = total += input.data[ indexIn ]& 0xFF;

				output.data[indexOut] = (byte)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void horizontal( ImageSInt16 input , ImageInt16 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final int divisor = kernelWidth;
		final int halfDivisor = divisor/2;

		for( int y = 0; y < input.height; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			int total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] ;
			}
			output.data[indexOut++] = (short)((total+halfDivisor)/divisor);

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] ;
				total += input.data[ indexIn ] ;

				output.data[indexOut++] = (short)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void vertical( ImageSInt16 input , ImageInt16 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final int backStep = kernelWidth*input.stride;

		int divisor = kernelWidth;
		final int halfDivisor = divisor/2;
		int totals[] = new int[ input.width ];

		for( int x = 0; x < input.width; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			int total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] ;
			}
			totals[x] = total;
			output.data[indexOut] = (short)((total+halfDivisor)/divisor);
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride;
			int indexOut = output.startIndex + y*output.stride;

			for( int x = 0; x < input.width; x++ ,indexIn++,indexOut++) {
				int total = totals[ x ]  - (input.data[ indexIn - backStep ]);
				totals[ x ] = total += input.data[ indexIn ];

				output.data[indexOut] = (short)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void horizontal( ImageFloat32 input , ImageFloat32 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final float divisor = kernelWidth;

		for( int y = 0; y < input.height; y++ ) {
			int indexIn = input.startIndex + input.stride*y;
			int indexOut = output.startIndex + output.stride*y + radius;

			float total = 0;

			int indexEnd = indexIn + kernelWidth;
			
			for( ; indexIn < indexEnd; indexIn++ ) {
				total += input.data[indexIn] ;
			}
			output.data[indexOut++] = (total/divisor);

			indexEnd = indexIn + input.width - kernelWidth;
			for( ; indexIn < indexEnd; indexIn++ ) {
				total -= input.data[ indexIn - kernelWidth ] ;
				total += input.data[ indexIn ] ;

				output.data[indexOut++] = (total/divisor);
			}
		}
	}

	public static void vertical( ImageFloat32 input , ImageFloat32 output , int radius ) {
		final int kernelWidth = radius*2 + 1;

		final int backStep = kernelWidth*input.stride;

		float divisor = kernelWidth;
		float totals[] = new float[ input.width ];

		for( int x = 0; x < input.width; x++ ) {
			int indexIn = input.startIndex + x;
			int indexOut = output.startIndex + output.stride*radius + x;

			float total = 0;
			int indexEnd = indexIn + input.stride*kernelWidth;
			for( ; indexIn < indexEnd; indexIn += input.stride) {
				total += input.data[indexIn] ;
			}
			totals[x] = total;
			output.data[indexOut] = (total/divisor);
		}

		// change the order it is processed in to reduce cache misses
		for( int y = radius+1; y < output.height-radius; y++ ) {
			int indexIn = input.startIndex + (y+radius)*input.stride;
			int indexOut = output.startIndex + y*output.stride;

			for( int x = 0; x < input.width; x++ ,indexIn++,indexOut++) {
				float total = totals[ x ]  - (input.data[ indexIn - backStep ]);
				totals[ x ] = total += input.data[ indexIn ];

				output.data[indexOut] = (total/divisor);
			}
		}
	}

}
