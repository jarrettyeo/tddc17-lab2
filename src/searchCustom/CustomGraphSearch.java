package searchCustom;

import java.util.ArrayList;
import java.util.HashSet;

import searchShared.NodeQueue;
import searchShared.Problem;
import searchShared.SearchObject;
import searchShared.SearchNode;

import world.GridPos;

public class CustomGraphSearch implements SearchObject {

	private HashSet<SearchNode> explored; // Set of SearchNodes
	private NodeQueue frontier; // NodeQueue has attribute LinkedList of SearchNodes
	protected ArrayList<SearchNode> path; // ArrayList of SearchNode
	private boolean insertFront;

	/**
	 * The constructor tells graph search whether it should insert nodes to front or back of the frontier 
	 */
    public CustomGraphSearch(boolean bInsertFront) {
		insertFront = bInsertFront;
    }

	/**
	 * Implements "graph search", which is the foundation of many search algorithms
	 */
	public ArrayList<SearchNode> search(Problem p) {
		// The frontier is a queue of expanded SearchNodes not processed yet
		frontier = new NodeQueue();
		/// The explored set is a set of nodes that have been processed 
		explored = new HashSet<SearchNode>();
		// The start state is given
		GridPos startState = (GridPos) p.getInitialState();
		// Initialize the frontier with the start state  
		frontier.addNodeToFront(new SearchNode(startState));

		// Path will be empty until we find the goal.
		path = new ArrayList<SearchNode>();
		
		// Modification here
		while (!frontier.isEmpty()) {
			
			// Remove first node as currentNode
			SearchNode currentNode = frontier.removeFirst();
			
			// Get state of currentNode
			GridPos currentState = currentNode.getState();
			
			// If GoalState is currentState, the currentNode is found
			if (p.isGoalState(currentState)) {
				
				// Get path from current position which is the Root
				path = currentNode.getPathFromRoot();
				break;
				
			}
			
			// We mark currentNode to explored HashSet which contains nodes which have been processed
			explored.add(currentNode);
			
			// ============================================================================================
			
			// Get all current reachable States
			ArrayList<GridPos> currentReachableStates = p.getReachableStatesFrom(currentState);
			
			// Loop through all current reachable States
			for (int i = 0; i < currentReachableStates.size(); i++) {
				
				// Create a SearchNode
				SearchNode thisNode = new SearchNode(currentReachableStates.get(i), currentNode);
				
				// thisNode not explored, we need to queue it to frontier queue of expanded SearchNodes not visited yet
				if (!explored.contains(thisNode)) {
					
					if (insertFront)
						// true = add to front = DFS
						frontier.addNodeToFront(thisNode);
					else 
						// false = add to back = BFS
						frontier.addNodeToBack(thisNode);
					
				}
				
				explored.add(thisNode);
			}
			
		}
		
		return path;
	}

	public ArrayList<SearchNode> getPath() {
		return path;
	}

	public ArrayList<SearchNode> getFrontierNodes() {
		return new ArrayList<SearchNode>(frontier.toList());
	}
	public ArrayList<SearchNode> getExploredNodes() {
		return new ArrayList<SearchNode>(explored);
	}
	public ArrayList<SearchNode> getAllExpandedNodes() {
		ArrayList<SearchNode> allNodes = new ArrayList<SearchNode>();
		allNodes.addAll(getFrontierNodes());
		allNodes.addAll(getExploredNodes());
		return allNodes;
	}

}
