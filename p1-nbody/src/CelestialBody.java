

/**
 * Celestial Body class for NBody
 * Modified from original Planet class
 * used at Princeton and Berkeley
 * @author ola
 *
 * If you add code here, add yourself as @author below
 * @MichaelThomas
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 *
	 * @return
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Accessor for the x-velocity
	 * @return the value of this object's x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 *
	 * @return
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double d;
		d = Math.sqrt(Math.pow((myXPos - b.getX()), 2) + Math.pow((myYPos - b.getY()), 2)) ;
		return d;
	}

	public double calcForceExertedBy(CelestialBody b) {
		double f;
		f = 6.67*1e-11 * myMass * b.getMass() / Math.pow(calcDistance(b), 2);
		return f;
	}

	public double calcForceExertedByX(CelestialBody b) {
		double fByX;
		fByX = calcForceExertedBy(b) * (b.getX() - myXPos) / calcDistance(b);
		return fByX;
	}
	public double calcForceExertedByY(CelestialBody b) {
		double fByY;
		fByY = this.calcForceExertedBy(b) * (b.getY() - myYPos) / calcDistance(b);
		return fByY;
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for(CelestialBody b : bodies){
			if(! b.equals(this)){
				sum += calcForceExertedByX(b);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for(CelestialBody b : bodies){
			if(! b.equals(this)){
				sum += calcForceExertedByY(b);
			}
		}
		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax;
		double ay;
		ax = xforce / myMass;
		ay = yforce / myMass;

		double nvx;
		double nvy;
		nvx = myXVel + deltaT * ax;
		nvy = myYVel + deltaT * ay;

		double nx;
		double ny;
		nx = myXPos + deltaT * nvx;
		ny = myYPos + deltaT * nvy;

		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
