import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	/** Matrix representation of the original graph */
	private static int[][] graph;
	private static int[] parent;
	private static int totalWeight_MST;

	/**
	 * Find the node outside the current MST with minimum key value that can be
	 * reached from the nodes already included in the MST.
	 */
	private static int minimumKey(boolean[] nodes_MST, int[] minimumKeys, int numberOfNodes, int startNode) {
		int value_minimumKey = Integer.MAX_VALUE;
		int index_minimumKey = 0;
		for (int i = 1; i < numberOfNodes + 1; i++) {
			if (nodes_MST[i] == false && minimumKeys[i] < value_minimumKey) {
				value_minimumKey = minimumKeys[i];
				index_minimumKey = i;
			}
		}
		totalWeight_MST += value_minimumKey;

		return index_minimumKey;
	}

	private static void Prim_MST(int numberOfNodes, int startNode) {

		parent = new int[numberOfNodes + 1];
		parent[startNode] = startNode;
		/**
		 * "minimum key value of a node" = the minimum weight of an edge, connecting
		 * current MST to a node outside the current MST.
		 */
		int[] minimumKeys = new int[numberOfNodes + 1];
		Arrays.fill(minimumKeys, Integer.MAX_VALUE);
		minimumKeys[startNode] = 0;

		boolean[] nodes_MST = new boolean[numberOfNodes + 1];

		int numberOfEdges_MST = 0;
		while (numberOfEdges_MST < numberOfNodes) {

			int index_minimumKey = minimumKey(nodes_MST, minimumKeys, numberOfNodes, startNode);
			nodes_MST[index_minimumKey] = true;

			for (int i = 1; i < numberOfNodes + 1; i++) {
				if (graph[index_minimumKey][i] != -1 && nodes_MST[i] == false
						&& graph[index_minimumKey][i] < minimumKeys[i]) {
					parent[i] = index_minimumKey;
					minimumKeys[i] = graph[index_minimumKey][i];
				}
			}
			numberOfEdges_MST++;
		}
	}

	private static void addUndirectedEdge(int nodeOne, int nodeTwo, int weight) {
		graph[nodeOne][nodeTwo] = weight;
		graph[nodeTwo][nodeOne] = weight;
	}

	/**
	 * There are test cases where an edge connecting two different nodes can have a
	 * value of zero. Therefore, the original graph has to be initialized with
	 * values that are less than zero.
	 */
	private static void initializeGraph(int numberOfNodes) {
		/**
		 * 1 <= value of a node <= numberOfNodes.
		 */
		graph = new int[numberOfNodes + 1][numberOfNodes + 1];

		for (int i = 0; i < graph.length; i++) {
			Arrays.fill(graph[i], -1);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int numberOfNodes = scanner.nextInt();
		int numberOfEdges = scanner.nextInt();

		initializeGraph(numberOfNodes);

		for (int i = 0; i < numberOfEdges; i++) {
			int nodeOne = scanner.nextInt();
			int nodeTwo = scanner.nextInt();
			int weight = scanner.nextInt();
			addUndirectedEdge(nodeOne, nodeTwo, weight);
		}

		int startNode = scanner.nextInt();
		scanner.close();

		Prim_MST(numberOfNodes, startNode);
		System.out.println(totalWeight_MST);
	}
}
