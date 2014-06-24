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

package sg.edu.nus.comp.klttracker.boofcv.alg.filter.binary;

import sg.edu.nus.comp.klttracker.boofcv.alg.InputSanityCheck;
import sg.edu.nus.comp.klttracker.boofcv.alg.filter.blur.BlurImageOps;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.*;

/**
 * <p>
 * Operations for thresholding images and converting them into a binary image.
 * </p>
 *
 * <p>
 * WARNING: Do not modify.  Automatically generated by {@link boofcv.alg.filter.binary.GenerateThresholdImageOps}.
 * </p>
 *
 * @author Peter Abeles
 */
public class ThresholdImageOps {

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageFloat32 input , ImageUInt8 output ,
										float threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageFloat64 input , ImageUInt8 output ,
										double threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageUInt8 input , ImageUInt8 output ,
										int threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]& 0xFF) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]& 0xFF) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageSInt16 input , ImageUInt8 output ,
										int threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageUInt16 input , ImageUInt8 output ,
										int threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]& 0xFFFF) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]& 0xFFFF) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values <=
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >=
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality <= is used, otherwise if false then >= is used.
	 * @return Output image.
	 */
	public static ImageUInt8 threshold( ImageSInt32 input , ImageUInt8 output ,
										int threshold , boolean down )
	{
		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++ ) {
					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Thresholds the image using an adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the average value of the surrounding pixels plus the bias.
	 * If down is true then b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param bias Bias used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param storage1 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @param storage2 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @return Thresholded image.
	 */
	public static ImageUInt8 adaptiveSquare( ImageUInt8 input , ImageUInt8 output ,
											 int radius , int bias , boolean down ,
											 ImageUInt8 storage1 , ImageUInt8 storage2 ) {

		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);
		storage1 = InputSanityCheck.checkDeclare(input,storage1,ImageUInt8.class);
		storage2 = InputSanityCheck.checkDeclare(input,storage2,ImageUInt8.class);

		ImageUInt8 mean = storage1;

		BlurImageOps.mean(input,mean,radius,storage2);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = mean.startIndex + y*mean.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					int threshold = (mean.data[indexMean]& 0xFF) + bias;

					if( (input.data[indexIn]& 0xFF) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = mean.startIndex + y*mean.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					int threshold = (mean.data[indexMean]& 0xFF) + bias;

					if( (input.data[indexIn]& 0xFF) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Thresholds the image using an adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the gaussian weighted sum of the surrounding pixels plus the bias.
	 * If down is true then b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param bias Bias used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param storage1 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @param storage2 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @return Thresholded image.
	 */
	public static ImageUInt8 adaptiveGaussian( ImageUInt8 input , ImageUInt8 output ,
											   int radius , int bias , boolean down ,
											   ImageUInt8 storage1 , ImageUInt8 storage2 ) {

		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);
		storage1 = InputSanityCheck.checkDeclare(input,storage1,ImageUInt8.class);
		storage2 = InputSanityCheck.checkDeclare(input,storage2,ImageUInt8.class);

		ImageUInt8 blur = storage1;

		BlurImageOps.gaussian(input,blur,-1,radius,storage2);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = blur.startIndex + y*blur.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					int threshold = (blur.data[indexMean]& 0xFF) + bias;

					if( (input.data[indexIn]& 0xFF) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = blur.startIndex + y*blur.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					int threshold = (blur.data[indexMean]& 0xFF) + bias;

					if( (input.data[indexIn]& 0xFF) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Thresholds the image using an adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the average value of the surrounding pixels plus the bias.
	 * If down is true then b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param bias Bias used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param storage1 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @param storage2 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @return Thresholded image.
	 */
	public static ImageUInt8 adaptiveSquare( ImageFloat32 input , ImageUInt8 output ,
											 int radius , float bias , boolean down ,
											 ImageFloat32 storage1 , ImageFloat32 storage2 ) {

		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);
		storage1 = InputSanityCheck.checkDeclare(input,storage1,ImageFloat32.class);
		storage2 = InputSanityCheck.checkDeclare(input,storage2,ImageFloat32.class);

		ImageFloat32 mean = storage1;

		BlurImageOps.mean(input,mean,radius,storage2);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = mean.startIndex + y*mean.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					float threshold = (mean.data[indexMean]) + bias;

					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = mean.startIndex + y*mean.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					float threshold = (mean.data[indexMean]) + bias;

					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}

	/**
	 * Thresholds the image using an adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the gaussian weighted sum of the surrounding pixels plus the bias.
	 * If down is true then b(x,y) = I(x,y) <= T(x,y) + bias ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) >= T(x,y) + bias ? 0 : 1
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param bias Bias used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param storage1 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @param storage2 (Optional) Storage for intermediate step. If null will be declared internally.
	 * @return Thresholded image.
	 */
	public static ImageUInt8 adaptiveGaussian( ImageFloat32 input , ImageUInt8 output ,
											   int radius , float bias , boolean down ,
											   ImageFloat32 storage1 , ImageFloat32 storage2 ) {

		output = InputSanityCheck.checkDeclare(input,output,ImageUInt8.class);
		storage1 = InputSanityCheck.checkDeclare(input,storage1,ImageFloat32.class);
		storage2 = InputSanityCheck.checkDeclare(input,storage2,ImageFloat32.class);

		ImageFloat32 blur = storage1;

		BlurImageOps.gaussian(input,blur,-1,radius,storage2);

		if( down ) {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = blur.startIndex + y*blur.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					float threshold = (blur.data[indexMean]) + bias;

					if( (input.data[indexIn]) <= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		} else {
			for( int y = 0; y < input.height; y++ ) {
				int indexIn = input.startIndex + y*input.stride;
				int indexOut = output.startIndex + y*output.stride;
				int indexMean = blur.startIndex + y*blur.stride;

				int end = indexIn + input.width;

				for( ; indexIn < end; indexIn++ , indexOut++, indexMean++ ) {
					float threshold = (blur.data[indexMean]) + bias;

					if( (input.data[indexIn]) >= threshold )
						output.data[indexOut] = 1;
					else
						output.data[indexOut] = 0;
				}
			}
		}

		return output;
	}


}
