
public class Ex {
    public static void main(String[] args) {
        AdjacencyListGraph<String> alg = new AdjacencyListGraph<>(5);
        alg.insertVertex("A"); // 0
        alg.insertVertex("B"); // 1
        alg.insertVertex("C"); // 2
        alg.insertVertex("D"); // 3
        alg.insertVertex("E"); // 4

        alg.insertEdge(1, 0, 1);
        alg.insertEdge(1, 2, 1);
        alg.insertEdge(0, 3, 1);
        alg.insertEdge(2, 3, 1);
        alg.insertEdge(3, 4, 1);

        System.out.println("Depth First Search: " + alg.depthFirstSearch(1));
        System.out.println("Breadth First Search: " + alg.breadthFirstSearch(1));
        //System.out.println(alg.toString());
        
    }
}
