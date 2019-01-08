package geometries;
import java.util.Map; 
import java.util.List;
import primitives.Ray;
import primitives.Point3D;

public interface Intersectable {
	public Map<Geometry,List<Point3D>> findIntersections(Ray rl);
}
