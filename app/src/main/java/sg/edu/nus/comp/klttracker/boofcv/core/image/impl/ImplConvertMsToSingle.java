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

package sg.edu.nus.comp.klttracker.boofcv.core.image.impl;

import sg.edu.nus.comp.klttracker.boofcv.struct.image.*;

/**
 * Low level implementations of different methods for converting {@link MultiSpectral} into
 * {@link boofcv.struct.image.ImageSingleBand}.
 * 
 * <ul>
 * <li>Average computes the average value of each pixel across the bands.
 * </ul>
 * 
 * <p>
 * DO NOT MODIFY: This class was automatically generated by {@link boofcv.core.image.impl.GenerateImplConvertMsToSingle}
 * </p>
 *
 * @author Peter Abeles
 */
public class ImplConvertMsToSingle {

	public static void average( MultiSpectral<ImageUInt8> from , ImageUInt8 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageUInt8 band0 = from.getBand(0);
			ImageUInt8 band1 = from.getBand(1);
			ImageUInt8 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = band0.data[indexFrom]& 0xFF;
					sum += band1.data[indexFrom]& 0xFF;
					sum += band2.data[indexFrom]& 0xFF;

					to.data[indexTo++] = (byte)(sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom]& 0xFF;
					}
					to.data[indexTo++] = (byte)(sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageSInt8> from , ImageSInt8 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageSInt8 band0 = from.getBand(0);
			ImageSInt8 band1 = from.getBand(1);
			ImageSInt8 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (byte)(sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (byte)(sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageUInt16> from , ImageUInt16 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageUInt16 band0 = from.getBand(0);
			ImageUInt16 band1 = from.getBand(1);
			ImageUInt16 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = band0.data[indexFrom]& 0xFFFF;
					sum += band1.data[indexFrom]& 0xFFFF;
					sum += band2.data[indexFrom]& 0xFFFF;

					to.data[indexTo++] = (short)(sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom]& 0xFFFF;
					}
					to.data[indexTo++] = (short)(sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageSInt16> from , ImageSInt16 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageSInt16 band0 = from.getBand(0);
			ImageSInt16 band1 = from.getBand(1);
			ImageSInt16 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (short)(sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (short)(sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageSInt32> from , ImageSInt32 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageSInt32 band0 = from.getBand(0);
			ImageSInt32 band1 = from.getBand(1);
			ImageSInt32 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					int sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageSInt64> from , ImageSInt64 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageSInt64 band0 = from.getBand(0);
			ImageSInt64 band1 = from.getBand(1);
			ImageSInt64 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					long sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					long sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageFloat32> from , ImageFloat32 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageFloat32 band0 = from.getBand(0);
			ImageFloat32 band1 = from.getBand(1);
			ImageFloat32 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					float sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					float sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (sum/numBands);
				}
			}
		}
	}

	public static void average( MultiSpectral<ImageFloat64> from , ImageFloat64 to ) {
		int numBands = from.getNumBands();

		if( numBands == 1 ) {
			to.setTo(from.getBand(0));
		} else if( numBands == 3 ) {
			ImageFloat64 band0 = from.getBand(0);
			ImageFloat64 band1 = from.getBand(1);
			ImageFloat64 band2 = from.getBand(2);

			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					double sum = band0.data[indexFrom];
					sum += band1.data[indexFrom];
					sum += band2.data[indexFrom];

					to.data[indexTo++] = (sum/3);
				}
			}
		} else {
			for (int y = 0; y < from.height; y++) {
				int indexFrom = from.getIndex(0, y);
				int indexTo = to.getIndex(0, y);

				for (int x = 0; x < from.width; x++ , indexFrom++ ) {
					double sum = 0;
					for( int b = 0; b < numBands; b++ ) {
						sum +=  from.bands[b].data[indexFrom];
					}
					to.data[indexTo++] = (sum/numBands);
				}
			}
		}
	}


}
