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

package sg.edu.nus.comp.klttracker.boofcv.abst.feature.detect.interest;

import sg.edu.nus.comp.klttracker.boofcv.abst.feature.detect.extract.ConfigExtract;

/**
 * Configuration for {@link boofcv.alg.feature.detect.interest.GeneralFeatureDetector}.
 *
 * @author Peter Abeles
 */
public class ConfigGeneralDetector extends ConfigExtract {

	/**
	 * Specifies the maximum number of features it will detect.  If a value is specified then the 'maxFeature' most
	 * intense features are returned.  Set to a value <= 0 to return all selected features.  Default is -1.
	 */
	public int maxFeatures = -1;

	public ConfigGeneralDetector(int maxFeatures ,
								 int radius, float threshold, int ignoreBorder, boolean useStrictRule,
								 boolean detectMinimums, boolean detectMaximums) {
		super(radius, threshold, ignoreBorder, useStrictRule, detectMinimums, detectMaximums);
		this.maxFeatures = maxFeatures;
	}

	public ConfigGeneralDetector(int maxFeatures, int radius, float threshold, int ignoreBorder, boolean useStrictRule) {
		super(radius, threshold, ignoreBorder, useStrictRule);
		this.maxFeatures = maxFeatures;
	}

	public ConfigGeneralDetector(int maxFeatures, int radius, float threshold) {
		super(radius, threshold);
		this.maxFeatures = maxFeatures;
	}

	public ConfigGeneralDetector(int maxFeatures , ConfigExtract config ) {
		this.maxFeatures = maxFeatures;
		if( config != null ) {
			super.setTo(config);
		}
	}

	public ConfigGeneralDetector() {
	}
}
