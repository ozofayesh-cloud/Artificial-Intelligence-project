import java.util.*;

class Node implements Comparable<Node> {
    String name;
    int f;
    int g;
    List<String> path;

    public Node(String name, int f, int g, List<String> path) {
        this.name = name;
        this.f = f;
        this.g = g;
        this.path = path;
    }

    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}

class Pair {
    String node;
    int cost;

    public Pair(String node, int cost) {
        this.node = node;
        this.cost = cost;
    }
}

public class SimpleAStar {
    public static void main(String[] args) {

        Map<String, List<Pair>> graph = new HashMap<>();
        graph.put("A", Arrays.asList(new Pair("B", 1), new Pair("C", 3)));
        graph.put("B", Arrays.asList(new Pair("C", 1), new Pair("G", 9)));
        graph.put("C", Arrays.asList(new Pair("B", 1), new Pair("D", 7), new Pair("G", 5), new Pair("H", 2)));
        graph.put("D", Arrays.asList(new Pair("H", 9)));
        graph.put("G", new ArrayList<>());
        graph.put("H", Arrays.asList(new Pair("G", 1), new Pair("D", 9)));

        Map<String, Integer> h = new HashMap<>();
        h.put("A", 5);
        h.put("B", 4);
        h.put("C", 1);
        h.put("D", 6);
        h.put("G", 0);
        h.put("H", 1);

        String start = "A";
        String goal = "G";

        PriorityQueue<Node> open = new PriorityQueue<>();
        Set<String> closed = new HashSet<>();
        Map<String, Integer> gScore = new HashMap<>();

        gScore.put(start, 0);
        open.add(new Node(start, h.get(start), 0, new ArrayList<>(Arrays.asList(start))));

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.name.equals(goal)) {
                System.out.println("Final Path: " + String.join("→", current.path));
                System.out.println("Total Cost: " + current.g);
                break;
            }

            if (closed.contains(current.name)) continue;

            closed.add(current.name);

            for (Pair neighbor : graph.get(current.name)) {
                int newG = current.g + neighbor.cost;

                if (!gScore.containsKey(neighbor.node) || newG < gScore.get(neighbor.node)) {
                    gScore.put(neighbor.node, newG);
                    int f = newG + h.get(neighbor.node);
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(neighbor.node);
                    open.add(new Node(neighbor.node, f, newG, newPath));
                }
            }
        }
    }
}
