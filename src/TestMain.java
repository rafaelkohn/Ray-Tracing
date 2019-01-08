/*import java.util.Scanner;
import geometries.*;
import primitives.*;


public class TestMain {
	static double getFPInput() {
		Scanner myVar = new Scanner(System.in);
		return myVar.nextDouble();
	}
	static Point3D getPoint3DInput() {
		System.out.println("please enter the three coordinates for your point");
		Coordinate Xcoord = new Coordinate(getFPInput());
		Coordinate Ycoord = new Coordinate(getFPInput());
		Coordinate Zcoord = new Coordinate(getFPInput());
		return new Point3D(Xcoord , Ycoord , Zcoord);
	}
	public static void main(String[] args) {
		try {
			Point3D pts[] = new Point3D[7];
			Vector vecs[] = new Vector[2];
			double radius[] = new double[2];
			double Height = getFPInput();
			//recieving input for the geometries:
			for(int i=0; i<7;i++) {
				pts[i] = getPoint3DInput();
			}
			for(int i=0;i<2;i++) {
				System.out.println("enter the vector's head:");
				vecs[i] = new Vector(getPoint3DInput());
				System.out.println("and now the radius:");
				radius[i] = getFPInput();
			}
			Ray _ray = new Ray(pts[6] , vecs[1]);
			//those are the main geometries:
			Plane _plane = new Plane(pts[0] , vecs[0]);
			Triangle _triangle = new Triangle(pts[1] , pts[2] , pts[3]);
			Sphere _sphere = new Sphere(radius[0] , pts[4]);
			Cylinder _cylinder = new Cylinder(radius[1], pts[5] , _ray , Height);
			//the operations:
			System.out.println("Now the details about the Geometries will be printed in the correct order:");
			System.out.println("The Plane:\nIntersetion Point: "+_plane.get_p()+" Normal Vector: "+_plane.getNormal());
			System.out.println("The Triangle:\nVertex A: "+_triangle.getVertexA()+" Vertex B: "+_triangle.getVertexB()+" Vertex C: "+_triangle.getVertexc());
			System.out.println("The Sphere:\nRadius: "+_sphere.getRadius()+" Center of the sphere: "+_sphere.get_center());
			System.out.println("the Cylinder:\nRadius: "+_cylinder.getRadius()+" Axis Point: "+_cylinder.get_axisPoint()+" Axis Direction: "+_cylinder.get_axisDirection()+" Height: "+_cylinder.get_height());
			
			System.out.println("Now we will see if our getNormal functions return Null as expected, please insert the 3D points:");
			if(_plane.getNormal(getPoint3DInput())==null)
				System.out.println("The Plane's function works as expected");
			else
				System.out.println("The Plane's function does not work as expected");
			if(_triangle.getNormal(getPoint3DInput())==null)
				System.out.println("The Triangle's function works as expected");
			else
				System.out.println("The Triangle's function does not work as expected");
			if(_sphere.getNormal(getPoint3DInput())==null)
				System.out.println("The Sphere's function works as expected");
			else
				System.out.println("The Sphere's function does not work as expected");
			if(_cylinder.getNormal(getPoint3DInput())==null)
				System.out.println("The Cylinder's function works as expected");
			else
				System.out.println("The Cylinder's function does not work as expected");

		}
		catch(ArithmeticException e) {
			System.out.println("it appears you have entered a Zero Vector. let's start over");
		}
		catch(Exception e)	{
			System.out.println("an Error occured!");
		}
	}

}
*/
