/*
 * Copyright (C) 2011-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Geometric Regression Library (GeoRegression).
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

package georegression.struct.shapes;

import georegression.struct.line.LineParametric3D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.point.Vector3D_F64;

import java.io.Serializable;

/**
 * Defines a cylinder in 3D space using a point and vector, which defines a line, and a radius around the line.
 *
 * @author Peter Abeles
 */
public class Cylinder3D_F64 implements Serializable {
	/**
	 * Line which defines the cylinder's axis
	 */
	public LineParametric3D_F64 line;
	/**
	 * Radius of the cylinder
	 */
	public double radius;

	public Cylinder3D_F64() {
		line = new LineParametric3D_F64();
	}

	public Cylinder3D_F64( double x_0, double y_0, double z_0,
						   double slopeX, double slopeY, double slopeZ,
						   double radius ) {
		this();
		this.line.set(x_0,y_0,z_0,slopeX,slopeY,slopeZ);
		this.radius = radius;
	}

	public Cylinder3D_F64(LineParametric3D_F64 line , double radius) {
		this();
		set(line,radius);
	}

	public Cylinder3D_F64(Cylinder3D_F64 o) {
		this();
		set(o);
	}

	public Cylinder3D_F64( boolean declare ) {
		if( declare )
			line = new LineParametric3D_F64();
	}

	public void set( double x_0, double y_0, double z_0,
					 double slopeX, double slopeY, double slopeZ,
					 double radius ) {
		this.line.set(x_0,y_0,z_0,slopeX,slopeY,slopeZ);
		this.radius = radius;
	}

	public void set( LineParametric3D_F64 line , double radius ) {
		this.line.set(line);
		this.radius = radius;
	}

	public void set( Cylinder3D_F64 o ) {
		this.line.set(o.line);
		this.radius = o.radius;
	}


	public String toString() {
		Point3D_F64 p = line.p;
		Vector3D_F64 slope = line.slope;

		return getClass().getSimpleName()+" P( "+p.x+" "+p.y+" "+p.z+" ) Slope( "+slope.x+" "+slope.y+" "+slope.z+" ) radius "+radius;
	}
}
