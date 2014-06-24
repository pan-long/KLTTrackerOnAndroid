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

package sg.edu.nus.comp.klttracker.boofcv.abst.feature.describe;

import sg.edu.nus.comp.klttracker.boofcv.alg.feature.describe.DescribePointBrief;
import sg.edu.nus.comp.klttracker.boofcv.struct.feature.TupleDesc_B;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSingleBand;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageType;

/**
 * @author Peter Abeles
 */
public class WrapDescribeBrief<T extends ImageSingleBand> implements DescribeRegionPoint<T,TupleDesc_B> {

	int length;
	DescribePointBrief<T> alg;
	ImageType<T> imageType;

	public WrapDescribeBrief( DescribePointBrief<T> alg , Class<T> imageType) {
		this.alg = alg;
		this.length = alg.getDefinition().getLength();
		this.imageType = ImageType.single(imageType);
	}

	@Override
	public TupleDesc_B createDescription() {
		return new TupleDesc_B(length);
	}

	@Override
	public void setImage(T image) {
		alg.setImage(image);
	}
	@Override
	public boolean process(double x, double y, double orientation, double scale, TupleDesc_B storage)
	{
		alg.process(x, y, storage);
		return true;
	}

	@Override
	public boolean requiresScale() {
		return false;
	}

	@Override
	public boolean requiresOrientation() {
		return false;
	}

	@Override
	public ImageType<T> getImageType() {
		return imageType;
	}

	@Override
	public Class<TupleDesc_B> getDescriptionType() {
		return TupleDesc_B.class;
	}
}