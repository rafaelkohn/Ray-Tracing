package elements;
import primitives.*;

public interface LightSource {
	
	public Color getIntensity(Point3D point);
	
	public Vector getL(Point3D point);
	
	public Vector getD(Point3D point);
	
}
