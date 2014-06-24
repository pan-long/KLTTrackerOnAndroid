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

package sg.edu.nus.comp.klttracker.boofcv.alg.feature.detect.line.gridline;


import georegression.struct.point.Point2D_F32;

/**
 * Describes an edge pixel found inside a region.
 *
 * @author Peter Abeles
 */
public class Edgel extends Point2D_F32 {
	// orientation of the edge
	// half circle from -pi to pi
	public float theta;

	public Edgel(float x, float y, float theta) {
		super(x, y);
		this.theta = theta;
	}

	public Edgel() {
	}
}