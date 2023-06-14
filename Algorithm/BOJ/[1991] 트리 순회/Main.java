import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static class Tree {
		static class Node {
			String value;
			Node leftChild;
			Node rightChild;
			
			Node() {
				leftChild = null;
				rightChild = null;
			}
			
			Node(String value) {
				this.value = value;
				leftChild = null;
				rightChild = null;
			}
			
			boolean hasLeft() {
				return leftChild != null;
			}
			
			boolean hasRight() {
				return rightChild != null;
			}
			
			void setLeft(Node leftChild) {
				this.leftChild = leftChild;
			}
			
			void setRight(Node rightChild) {
				this.rightChild = rightChild;
			}
		}
		
		Node root;
		
		Tree() {
			root = new Node("A");
		}
		
		private Node findNode(String value) {
			Stack<Node> stack = new Stack<>();
			stack.push(root);
			
			while (!stack.empty()) {
				Node node = stack.pop();
				
				if (node.value.equals(value)) {
					return node;
				}
				
				if (node.hasLeft()) {
					stack.push(node.leftChild);
				}
				if (node.hasRight()) {
					stack.push(node.rightChild);
				}
			}
			
			return null;
		}
		
		void insert(String value, String left, String right) {
			Node parent = findNode(value);
			
			if (parent == null) {
				System.out.println("해당 노드가 없습니다. 트리에 ...");
				return;
			}
			
			if (left.equals(".") && right.equals(".")) {
				return;
			}
			
			if (left.equals(".")) {
				parent.setRight(new Node(right));
				return;
			} else if (right.equals(".")) {
				parent.setLeft(new Node(left));
				return;
			}

			parent.setLeft(new Node(left));
			parent.setRight(new Node(right));
		}
		
		void preOrder(Node current) {
			System.out.print(current.value);
			
			if (current.hasLeft()) {
				preOrder(current.leftChild);
			}
			if (current.hasRight()) {
				preOrder(current.rightChild);
			}
		}
		
		void inOrder(Node current) {
			if (current.hasLeft()) {
				inOrder(current.leftChild);
			}
			System.out.print(current.value);
			if (current.hasRight()) {
				inOrder(current.rightChild);
			}
		}
		
		void postOrder(Node current) {
			if (current.hasLeft()) {
				postOrder(current.leftChild);
			}
			if (current.hasRight()) {
				postOrder(current.rightChild);
			}
			System.out.print(current.value);
		}
	}
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	Tree tree = new Tree();
    	int N = Integer.parseInt(br.readLine());
    	StringTokenizer st;
    	
    	for (int index = 0; index < N; index++) {
        	st = new StringTokenizer(br.readLine());
        	String parent = st.nextToken();
        	String left = st.nextToken();
        	String right = st.nextToken();
        	
        	tree.insert(parent, left, right);
    	}
    	
    	tree.preOrder(tree.root);
    	System.out.println();
    	tree.inOrder(tree.root);
    	System.out.println();
    	tree.postOrder(tree.root);
    }
}