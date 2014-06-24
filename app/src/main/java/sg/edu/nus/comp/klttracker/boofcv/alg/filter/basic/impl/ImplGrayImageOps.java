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

package sg.edu.nus.comp.klttracker.boofcv.alg.filter.basic.impl;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.*;

/**
 * <p>
 * Contains implementations of algorithms in {@link boofcv.alg.filter.basic.GrayImageOps}.
 * </p>
 * 
 * <p>
 * WARNING: Do not modify.  Automatically generated by {@link GenerateImplGrayImageOps}.
 * </p>
 */
public class ImplGrayImageOps {

	public static void invert(ImageFloat32 input, float max , ImageFloat32 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(ImageFloat32 input, float beta, float max , ImageFloat32 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				float val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void stretch(ImageFloat32 input, double gamma, float beta, float max , ImageFloat32 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				float val = (float)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void invert(ImageUInt8 input, int max , ImageUInt8 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (byte)(max - (input.data[indexSrc++]& 0xFF));
			}
		}
	}

	public static void brighten(ImageUInt8 input, int beta, int max , ImageUInt8 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]& 0xFF) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (byte)val;
			}
		}
	}

	public static void stretch(ImageUInt8 input, double gamma, int beta, int max , ImageUInt8 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++]& 0xFF)* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (byte)val;
			}
		}
	}

	public static void invert(ImageSInt16 input, int max , ImageSInt16 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (short)(max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(ImageSInt16 input, int beta, int max , ImageSInt16 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void stretch(ImageSInt16 input, double gamma, int beta, int max , ImageSInt16 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void invert(ImageUInt16 input, int max , ImageUInt16 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (short)(max - (input.data[indexSrc++]& 0xFFFF));
			}
		}
	}

	public static void brighten(ImageUInt16 input, int beta, int max , ImageUInt16 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]& 0xFFFF) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void stretch(ImageUInt16 input, double gamma, int beta, int max , ImageUInt16 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++]& 0xFFFF)* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void invert(ImageSInt32 input, int max , ImageSInt32 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(ImageSInt32 input, int beta, int max , ImageSInt32 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void stretch(ImageSInt32 input, double gamma, int beta, int max , ImageSInt32 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}


}
