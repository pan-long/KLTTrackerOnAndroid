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

package sg.edu.nus.comp.klttracker.boofcv.abst.segmentation;

import sg.edu.nus.comp.klttracker.boofcv.alg.segmentation.slic.SegmentSlic;
import sg.edu.nus.comp.klttracker.boofcv.struct.ConnectRule;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageBase;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageSInt32;
import sg.edu.nus.comp.klttracker.boofcv.struct.image.ImageType;

/**
 * Wrapper around {@link SegmentSlic} for {@link ImageSuperpixels}.
 *
 * @author Peter Abeles
 */
public class Slic_to_ImageSuperpixels<T extends ImageBase> implements ImageSuperpixels<T> {

	SegmentSlic<T> slic;

	public Slic_to_ImageSuperpixels(SegmentSlic<T> slic) {
		this.slic = slic;
	}

	@Override
	public void segment(T input, ImageSInt32 output) {
		slic.process(input,output);
	}

	@Override
	public int getTotalSuperpixels() {
		return slic.getRegionMemberCount().size;
	}

	@Override
	public ConnectRule getRule() {
		return slic.getConnectRule();
	}

	@Override
	public ImageType<T> getImageType() {
		return slic.getImageType();
	}
}