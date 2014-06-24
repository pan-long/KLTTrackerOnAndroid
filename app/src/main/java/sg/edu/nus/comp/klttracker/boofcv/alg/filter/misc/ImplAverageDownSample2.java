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

package sg.edu.nus.comp.klttracker.boofcv.alg.filter.misc;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.*;

/**
 * <p>Implementation of {@link AverageDownSampleOps} specialized for square regions of width 2.</p>
 *
 * <p>
 * DO NOT MODIFY: This class was automatically generated by {@link boofcv.alg.filter.misc.GenerateImplAverageDownSample2}.
 * </p>
 *
 * @author Peter Abeles
 */
public class ImplAverageDownSample2 {
	public static void down( ImageUInt8 input , ImageInt8 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ]& 0xFF;
				total += input.data[ indexIn0++ ]& 0xFF;
				total += input.data[ indexIn1++ ]& 0xFF;
				total += input.data[ indexIn1++ ]& 0xFF;

				output.data[ indexOut++ ] = (byte)((total+2)/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				int total = input.data[ indexIn0 ]& 0xFF;
				total += input.data[ indexIn1 ]& 0xFF;

				output.data[ indexOut ] = (byte)((total+1)/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ]& 0xFF;
				total += input.data[ indexIn0++ ]& 0xFF;

				output.data[ indexOut++ ] = (byte)((total+1)/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageSInt8 input , ImageInt8 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];
				total += input.data[ indexIn1++ ];
				total += input.data[ indexIn1++ ];

				output.data[ indexOut++ ] = (byte)((total+2)/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				int total = input.data[ indexIn0 ];
				total += input.data[ indexIn1 ];

				output.data[ indexOut ] = (byte)((total+1)/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];

				output.data[ indexOut++ ] = (byte)((total+1)/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageUInt16 input , ImageInt16 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ]& 0xFFFF;
				total += input.data[ indexIn0++ ]& 0xFFFF;
				total += input.data[ indexIn1++ ]& 0xFFFF;
				total += input.data[ indexIn1++ ]& 0xFFFF;

				output.data[ indexOut++ ] = (short)((total+2)/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				int total = input.data[ indexIn0 ]& 0xFFFF;
				total += input.data[ indexIn1 ]& 0xFFFF;

				output.data[ indexOut ] = (short)((total+1)/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ]& 0xFFFF;
				total += input.data[ indexIn0++ ]& 0xFFFF;

				output.data[ indexOut++ ] = (short)((total+1)/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageSInt16 input , ImageInt16 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];
				total += input.data[ indexIn1++ ];
				total += input.data[ indexIn1++ ];

				output.data[ indexOut++ ] = (short)((total+2)/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				int total = input.data[ indexIn0 ];
				total += input.data[ indexIn1 ];

				output.data[ indexOut ] = (short)((total+1)/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];

				output.data[ indexOut++ ] = (short)((total+1)/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageSInt32 input , ImageSInt32 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];
				total += input.data[ indexIn1++ ];
				total += input.data[ indexIn1++ ];

				output.data[ indexOut++ ] = ((total+2)/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				int total = input.data[ indexIn0 ];
				total += input.data[ indexIn1 ];

				output.data[ indexOut ] = ((total+1)/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				int total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];

				output.data[ indexOut++ ] = ((total+1)/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageFloat32 input , ImageFloat32 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				float total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];
				total += input.data[ indexIn1++ ];
				total += input.data[ indexIn1++ ];

				output.data[ indexOut++ ] = (total/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				float total = input.data[ indexIn0 ];
				total += input.data[ indexIn1 ];

				output.data[ indexOut ] = (total/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				float total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];

				output.data[ indexOut++ ] = (total/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}

	public static void down( ImageFloat64 input , ImageFloat64 output ) {
		int maxY = input.height - input.height%2;
		int maxX = input.width - input.width%2;

		for( int y = 0; y < maxY; y += 2 ) {
			int indexOut = output.startIndex + (y/2)*output.stride;

			int indexIn0 = input.startIndex + y*input.stride;
			int indexIn1 = indexIn0 + input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				double total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];
				total += input.data[ indexIn1++ ];
				total += input.data[ indexIn1++ ];

				output.data[ indexOut++ ] = (total/4);
			}
		}

		if( maxX != input.width ) {
			for( int y = 0; y < maxY; y += 2 ) {
				int indexOut = output.startIndex + (y/2)*output.stride + output.width-1;

				int indexIn0 = input.startIndex + y*input.stride + maxX;
				int indexIn1 = indexIn0 + input.stride;

				double total = input.data[ indexIn0 ];
				total += input.data[ indexIn1 ];

				output.data[ indexOut ] = (total/2);
			}
		}

		if( maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride;

			int indexIn0 = input.startIndex + (input.height-1)*input.stride;

			for( int x = 0; x < maxX; x += 2 ) {
				double total = input.data[ indexIn0++ ];
				total += input.data[ indexIn0++ ];

				output.data[ indexOut++ ] = (total/2);
			}
		}

		if( maxX != input.width && maxY != input.height ) {
			int indexOut = output.startIndex + (output.height-1)*output.stride + output.width-1;
			int indexIn = input.startIndex + (input.height-1)*input.stride + input.width-1;

			output.data[indexOut] = input.data[ indexIn ];
		}
	}


}
