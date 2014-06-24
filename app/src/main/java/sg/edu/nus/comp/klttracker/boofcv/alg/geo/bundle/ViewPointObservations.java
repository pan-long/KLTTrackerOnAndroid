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

package sg.edu.nus.comp.klttracker.boofcv.alg.geo.bundle;

import org.ddogleg.struct.FastQueue;

/**
 * A set of observations for a single view.
 *
 * @author Peter Abeles
 */
public class ViewPointObservations {


	FastQueue<PointIndexObservation> points;

	public ViewPointObservations() {
		points = new FastQueue<PointIndexObservation>(
				10,PointIndexObservation.class,true);
	}

	public FastQueue<PointIndexObservation> getPoints() {
		return points;
	}
}