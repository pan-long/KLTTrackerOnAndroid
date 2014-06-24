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

package sg.edu.nus.comp.klttracker.boofcv.alg.tracker.tld;

import sg.edu.nus.comp.klttracker.boofcv.alg.sfm.robust.DistanceScaleTranslate2DSq;
import sg.edu.nus.comp.klttracker.boofcv.alg.sfm.robust.GenerateScaleTranslate2D;
import sg.edu.nus.comp.klttracker.boofcv.alg.sfm.robust.ModelManagerScaleTranslate2D;
import sg.edu.nus.comp.klttracker.boofcv.struct.geo.AssociatedPair;
import sg.edu.nus.comp.klttracker.boofcv.struct.sfm.ScaleTranslate2D;
import georegression.struct.shapes.RectangleCorner2D_F64;
import org.ddogleg.fitting.modelset.DistanceFromModel;
import org.ddogleg.fitting.modelset.ModelGenerator;
import org.ddogleg.fitting.modelset.ModelManager;
import org.ddogleg.fitting.modelset.lmeds.LeastMedianOfSquares;
import org.ddogleg.struct.FastQueue;

/**
 * Adjusts the previous region using information from the region tracker.
 *
 * @author Peter Abeles
 */
public class TldAdjustRegion {

	// used for estimating motion from track locations
	private LeastMedianOfSquares<ScaleTranslate2D,AssociatedPair> estimateMotion;

	int imageWidth;
	int imageHeight;

	/**
	 *
	 * @param numCycles Number of iterations in robust motion estimation.  Try 50.
	 */
	public TldAdjustRegion( int numCycles ) {

		ModelManager<ScaleTranslate2D> manager = new ModelManagerScaleTranslate2D();
		ModelGenerator<ScaleTranslate2D,AssociatedPair> generator = new GenerateScaleTranslate2D();
		DistanceFromModel<ScaleTranslate2D,AssociatedPair> distance = new DistanceScaleTranslate2DSq();

		estimateMotion = new LeastMedianOfSquares<ScaleTranslate2D, AssociatedPair>(123123,numCycles,Double.MAX_VALUE,
				0,manager,generator,distance);
	}

	public void init( int imageWidth , int imageHeight ) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	/**
	 * Adjusts target rectangle using track information
	 *
	 * @param pairs List of feature location in previous and current frame.
	 * @param targetRectangle (Input) current location of rectangle.  (output) adjusted location
	 * @return true if successful
	 */
	public boolean process( FastQueue<AssociatedPair> pairs , RectangleCorner2D_F64 targetRectangle ) {
		// estimate how the rectangle has changed and update it
		if( !estimateMotion.process(pairs.toList()) )
			return false;

		ScaleTranslate2D motion = estimateMotion.getModelParameters();

		adjustRectangle(targetRectangle,motion);

		if( targetRectangle.x0 < 0 || targetRectangle.y0 < 0 )
			return false;

		if( targetRectangle.x1 >= imageWidth || targetRectangle.y1 >= imageHeight )
			return false;

		return true;
	}

	/**
	 * Estimate motion of points inside the rectangle and updates the rectangle using the found motion.
	 */
	protected void adjustRectangle( RectangleCorner2D_F64 rect , ScaleTranslate2D motion ) {
		rect.x0 = rect.x0*motion.scale + motion.transX;
		rect.y0 = rect.y0*motion.scale + motion.transY;
		rect.x1 = rect.x1*motion.scale + motion.transX;
		rect.y1 = rect.y1*motion.scale + motion.transY;
	}
}
