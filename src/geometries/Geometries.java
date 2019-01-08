package geometries;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import primitives.Point3D;
import primitives.Ray;

/**
 * the class is a collection of all of the geometries in the scene
 */
public class Geometries implements Intersectable {
	private List<Geometry> itsShapes = new ArrayList<>();
	
	// ***************** Constructors ********************** //
	/**
	 * Ctor, copy Ctor: copies another instance of this class into this one
	 * @param geometries the copied object
	 */
	public Geometries(Geometries geometries){
		for(Geometry geometry : geometries.itsShapes) 
			this.itsShapes.add(geometry);
	}
	
	/**
	 * Ctor, receives a list of Geometries and puts them here
	 * @param ItsShapes the list
	 */
	public Geometries(List<Geometry> ItsShapes){
		for(Geometry geometry : ItsShapes) 
			itsShapes.add(geometry);
	}
	
	/**
	 * empty ctor
	 */
	public Geometries() {}
	
	
	// ***************** Operations ********************** //
	
	/**
	 * adds the received geometry to the list
	 */
	public void add(Geometry geometry) {
		itsShapes.add(geometry);
	}
	
	/**
	 * makes a map with all of the intersection points with a received ray as value and the geometry as a key
	 * @param rl the ray
	 * @returns a map with all of the intersection points with the received ray
	 */
	public Map<Geometry,List<Point3D>> findIntersections(Ray rl){
		Map<Geometry, List<Point3D>> map = new HashMap<>();
		for (Geometry geometry : itsShapes) {
			if(geometry.findIntersections(rl) != null)
				map.putAll(geometry.findIntersections(rl));
		}
		return map;
	}
}
