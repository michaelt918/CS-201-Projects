import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileInputStream;

/**
 * Models a weighted graph of latitude-longitude points
 * and supports various distance and routing operations.
 * To do: Add your name(s) as additional authors
 * @author Brandon Fain
 *
 */
public class GraphProcessor {
    //instance variables
    private int num_vertices;
    private int num_edges;
    private ArrayList<Point> vertList = new ArrayList<Point>();;
    private HashMap<Point, List<Point>> vertGraph = new HashMap<Point, List<Point>>();
    private int[][] edgeArray;





    /**
     * Creates and initializes a graph from a source data
     * file in the .graph format. Should be called
     * before any other methods work.
     * @param file a FileInputStream of the .graph file
     * @throws Exception if file not found or error reading
     */
    
    /*
     * gpt rewritten
     * public void initialize(FileInputStream file) throws Exception {
    Scanner scanner = new Scanner(file);
    vertGraph = new HashMap<>();
    vertList = new ArrayList<>();
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        switch (parts[0]) {
            case "V":
                double latitude = Double.parseDouble(parts[1]);
                double longitude = Double.parseDouble(parts[2]);
                Point point = new Point(latitude, longitude);
                vertList.add(point);
                break;
            case "E":
                Point e1 = vertList.get(Integer.parseInt(parts[1]));
                Point e2 = vertList.get(Integer.parseInt(parts[2]));
                if (vertGraph.containsKey(e1)) {
                    List<Point> neighbors = vertGraph.get(e1);
                    neighbors.add(e2);
                    vertGraph.put(e1, neighbors);
                } else {
                    List<Point> neighbors = new ArrayList<>();
                    neighbors.add(e2);
                    vertGraph.put(e1, neighbors);
                }
                if (vertGraph.containsKey(e2)) {
                    List<Point> neighbors = vertGraph.get(e2);
                    neighbors.add(e1);
                    vertGraph.put(e2, neighbors);
                } else {
                    List<Point> neighbors = new ArrayList<>();
                    neighbors.add(e1);
                    vertGraph.put(e2, neighbors);
                }
                break;
        }
    }
    scanner.close();
}

     */
    
     public void initialize(FileInputStream file) throws Exception {
        // TODO: Implement initialize

        try {

        Scanner scanner = new Scanner(file);
        num_vertices = scanner.nextInt();
        num_edges = scanner.nextInt();

        for (int i = 0; i < num_vertices; i++) {

            scanner.next();
            double latitude = scanner.nextDouble();
            double longitude = scanner.nextDouble();
            vertList.add(new Point(latitude, longitude));
        }

        for (int i = 0; i < num_edges; i++) {
            Point e1 = vertList.get(scanner.nextInt());
            Point e2 = vertList.get(scanner.nextInt());
            if (scanner.hasNextLine()) 
            {
                scanner.nextLine();
            } else 
            {
                break;
            }
            if (vertGraph.containsKey(e1)) {
                List<Point> neighbors = vertGraph.get(e1);
                neighbors.add(e2);
                vertGraph.put(e1, neighbors);
            } else {
                List<Point> neighbors = new ArrayList<>();
                neighbors.add(e2);
                vertGraph.put(e1, neighbors);
            }
            if (vertGraph.containsKey(e2)) {
                List<Point> neighbors = vertGraph.get(e2);
                neighbors.add(e1);
                vertGraph.put(e2, neighbors);
            } else {
                List<Point> neighbors = new ArrayList<>();
                neighbors.add(e1);
                vertGraph.put(e2, neighbors);
            }
        }
        scanner.close();

        }
        catch (Exception e) 
        {
            throw new Exception("Could not read .graph file");
        }

    }


    /**
     * Searches for the point in the graph that is closest in
     * straight-line distance to the parameter point p
     * @param p A point, not necessarily in the graph
     * @return The closest point in the graph to p
     */
    public Point nearestPoint(Point p) {
        Point nearest = null;
        double min = Double.MAX_VALUE;
        for (Point fp: vertList) {
            double dist = p.distance(fp);
            if (dist < min) {
                min = dist;
                nearest = fp;
            }
        }
        return nearest;
    }


    /**
     * Calculates the total distance along the route, summing
     * the distance between the first and the second Points, 
     * the second and the third, ..., the second to last and
     * the last. Distance returned in miles.
     * @param start Beginning point. May or may not be in the graph.
     * @param end Destination point May or may not be in the graph.
     * @return The distance to get from start to end
     */
    public double routeDistance(List<Point> route) {
        double rDist = 0;
        for (int i = 0; i < route.size()-1; i++) {
            rDist += route.get(i+1).distance(route.get(i));
        }
        return rDist;
    }

    

    /**
     * Checks if input points are part of a connected component
     * in the graph, that is, can one get from one to the other
     * only traversing edges in the graph
     * @param p1 one point
     * @param p2 another point
     * @return true if p2 is reachable from p1 (and vice versa)
     */
    public boolean connected(Point p1, Point p2) {
        // TODO: Implement connected
        try{
            List<Point> path = route(p1, p2);
            return true;
        } 
        catch (InvalidAlgorithmParameterException e){
            return false;
        }

    }

    

    /**
     * Returns the shortest path, traversing the graph, that begins at start
     * and terminates at end, including start and end as the first and last
     * points in the returned list. If there is no such route, either because
     * start is not connected to end or because start equals end, throws an
     * exception.
     * @param start Beginning point.
     * @param end Destination point.
     * @return The shortest path [start, ..., end].
     * @throws InvalidAlgorithmParameterException if there is no such route, 
     * either because start is not connected to end or because start equals end.
     */
    public List<Point> route(Point start, Point end) throws InvalidAlgorithmParameterException {
        // TODO: Implement route
        HashMap<Point, Double> dMap = new HashMap<>();
        Comparator<Point> comp = (a, b) -> (int) (dMap.get(a) - dMap.get(b));
        PriorityQueue<Point> toExplore = new PriorityQueue<>(comp);
        HashMap<Point, Point> paths = new HashMap<>();
        Point currentA = start;
        dMap.put(currentA, 0.0);
        toExplore.add(currentA);

        while (!toExplore.isEmpty()) {

            currentA = toExplore.remove();

            for (Point p : vertGraph.get(currentA)) {

                double d = currentA.distance(p);
                
                if (!dMap.containsKey(p) || dMap.get(p) > dMap.get(currentA) + d) {
                    dMap.put(p, dMap.get(currentA) + d);
                    paths.put(p, currentA);
                    toExplore.add(p);
                }

            }

        }

        if (paths.get(end) == null) {
            throw new InvalidAlgorithmParameterException("No path between start and end");
        }

        ArrayList<Point> ret = new ArrayList<>();
        ret.add(end);
        Point currentB = paths.get(end);

        while (!currentB.equals(start)) {
        
            ret.add(currentB);
            currentB = paths.get(currentB);

        }

        ret.add(currentB);
        Collections.reverse(ret);

        return ret;
    }

    
}
