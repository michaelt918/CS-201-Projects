import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Demonstrates the calculation of shortest paths in the US Highway
 * network, showing the functionality of GraphProcessor and using
 * Visualize
 * To do: Add your name(s) as authors
 **/
public class GraphDemo {
    public static void main(String[] args) throws Exception {

        GraphProcessor map = new GraphProcessor();
        Map<String, Point> city;
        String image = "images/usa.png";
        String visual = "data/usa.vis";
        map.initialize(new FileInputStream("data/usa.graph"));
        city = findC("data/uscities.csv");

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter starting city (e.g., Philadelphia PA): ");
        String startLocation = scan.nextLine();

        System.out.print("Enter ending city (e.g., Durham NC): ");
        String endLocation = scan.nextLine();

        long timer = System.nanoTime();
        Point start = map.nearestPoint(city.get(startLocation));
        Point end = map.nearestPoint(city.get(endLocation));

        List<Point> route = map.route(start, end);
        double dist = map.routeDistance(route);
        long time = System.nanoTime() - timer;
        double tim = (double) time / (double) 1e9;

        System.out.println("Time:\t" + tim + " seconds");
        System.out.println("Distance:\t" + dist + " miles");

        Visualize vis = new Visualize(visual, image);

        vis.drawRoute(route);
        scan.close();
    }

    private static Map<String, Point> findC(String fileName) throws FileNotFoundException {
		Scanner scan2 = new Scanner(new File(fileName));
		Map<String, Point> result = new HashMap<>(); 
		while(scan2.hasNextLine()) {
			try {
				String[] data = scan2.nextLine().split(",");
				result.put(data[0] + " " + data[1], 
				new Point(Double.parseDouble(data[2]),
				Double.parseDouble(data[3])));
			}
            catch(Exception e) {
				continue;    
			}
		}
		return result;
	}   
}