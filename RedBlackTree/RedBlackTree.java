//Name: Maad Ahmed Abbasi
//UCID: 30127307
//Prof: Fong
//TA: Zhang
package ca.ucalgary.cpsc331.a2;

public class RedBlackTree implements Dictionary {
    private Node root;
    private Node NIL;
    private boolean RED = true;
    private boolean BLACK = false;
    

    /**
     * Constructor
     */
    public RedBlackTree() {
        NIL = new Node(-2147483648);
		root = null;
    }

    @Override
    public boolean empty() {
        if (root == null)
            return true;
        return false;
    }


    // search the tree for the key k
	// and return the corresponding node
    
    @Override
	public boolean member (int k) {
        Node x = root;
        while (x != null && x.key != k) {
            if (k < x.key)
                x = x.left;
            else 
                x = x.right;
        }
        if (x != null)
            return true;
        return false;
	}

    

    /**
     * USED TO FIX BINARY SEARCH TREE PROPERTIES IN A RED BLACK TREE
     * LEFT ROTATE AT X - O(1)
     * @param x
     */
    public void leftRotate (Node x) {

        Node y = x.right;
        
        // turn y ’s left subtree into x ’s right subtree
        x.right = y.left;

        // if y ’s left subtree is not empty then x becomes the parent of the subtree’s root        
        if (y.left != NIL)
            y.left.parent = x;
        
        // x's parent becomes y's parent
        y.parent = x.parent;

        // if x was the root then y becomes the root
        if(x.parent == null)
            root = y;
        
        // otherwise, if x was a left child then y becomes a left child
        else if(x == x.parent.left)
            x.parent.left = y;
        
        // otherwise, x was a right child, and now y is
        else
            x.parent.right = y;

        // make x become y's left child
        y.left = x;
        x.parent = y;
    }

    /**
     * USED TO FIX BINARY SEARCH TREE PROPERTIES IN A RED BLACK TREE
     * RIGHT ROTATE AT Y - O(1)
     * @param y
     */
    public void rightRotate (Node y) {

        Node x = y.left;

        // turn x ’s right subtree into y ’s left subtree
        y.left = x.right;

        // if x ’s right subtree is not empty then y becomes the parent of the subtree’s root        
        if (x.right != NIL)
            x.right.parent = y;
        
        // y's parent becomes x's parent
        x.parent = y.parent;

        // if y was the root then x becomes the root
        if (y.parent == null)
            this.root = x;
        
        // otherwise, if y was a right child then x becomes a right child
        else if(y == y.parent.right)
            y.parent.right = x;
        
        // otherwise, y was a left child, and now x is
        else
            y.parent.left = x;

        // make y become x's right child
        x.right = y;
        y.parent = x;
    }

    /**
     * 
     */
    @Override
    public boolean insert (int k) {
        // ORDINARY BST INSERTION

        // run member
        boolean isMember = member(k);
        
        // do not insert if it is a member
        if (isMember)
            return false;

        Node z = new Node(k); // the node to add
        z.parent = null;
        z.left = NIL;
        z.right = NIL;
        z.red = RED;
        
        Node y = null; // parent of z
        Node x = root; // node being compared with z

        // check if empty tree
        if (empty()) {
            root = z;
            z.red = BLACK;
            return true;
        }
        
        // proceed if not empty
        while (x != NIL) { // loop until leaf position is reached
            y = x;
            if (z.key < x.key)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = y; // found location - insert z with parent y

        // add z to tree
        if (z.key < y.key)
            y.left = z;
        else
            y.right = z;

        // if grandparent is null, no need to fix INSERT
        if (z.parent.parent == null)
            return true;

        // correct RBT violations
        insertFixup(z);

        return true;
    }

    private void insertFixup (Node z) {
        
        Node y;
        while (z.parent.red) {
            
            // is z's parent a left child?
            if (z.parent == z.parent.parent.left) {

                // y is z's uncle
                y = z.parent.parent.right;

                // are z's parent and uncle both red?
                if(y.red) {
                    // CASE 1
                    z.parent.red = BLACK;
                    y.red = BLACK;
                    z.parent.parent.red = RED;
                    z = z.parent.parent;
                }
                else {
                    if(z == z.parent.right) {
                        // CASE 2
                        z = z.parent;
                        leftRotate(z);
                    }
                    // CASE 3
                    z.parent.red = BLACK;
                    z.parent.parent.red = RED;
                    rightRotate(z.parent.parent);
                }
            }

            else {
				
                y = z.parent.parent.left; // uncle

				if (y.red) {
					// case 3.1
					y.red = BLACK;
					z.parent.red = BLACK;
					z.parent.parent.red = RED;
					z = z.parent.parent;
				} 
                
                else {
					if (z == z.parent.left) {
						// case 3.2.2
						z = z.parent;
						rightRotate(z);
					}
					// case 3.2.1
					z.parent.red = BLACK;
					z.parent.parent.red = RED;
					leftRotate(z.parent.parent);
				}
			}


            
            // stop after reaching root
            if (z == root)
                break;
        }
        
        root.red = BLACK;
    }

    /**
     * 
     * @param u
     * @param v
     */
    private void transplant (Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        v.parent = u.parent;
    }

    /**
     * 
     */
    private Node minimum (Node x) {
        while(x.left != NIL)
            x = x.left;
        return x;
    }


    @Override
	public boolean delete (int k) {

        // find the node containing key
		Node z = NIL;
        Node x, y;

        Node node = root;		
		while (node != NIL){
			if (node.key == k)
                z = node;
			if (node.key < k)
				node = node.right;
			else
				node = node.left;
		}

		if (z == NIL)
			return false;

        //
        y = z;
        boolean isYRed = y.red; // original color

        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right); // replace z by its right child
        }        
        else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left); // replace z by its left child
        }        
        else {            
            y = minimum(z.right); // y is z's successor
            isYRed = y.red;
            x = y.right;            
            
            // is y farther down the tree?
            if (y != z.right) {
                transplant(y, y.right); // replace y by its right child

                // z's right child becomes y's right child
                y.right = z.right;
                y.right.parent = y;
            }
            
            // when x is null --- ****
            else
                x.parent = y;
            
            transplant(z, y); // replace z by its successor y
            
            // give z's left child to y, which had no left child 
            y.left = z.left;
            y.left.parent = y;
            y.red = z.red;
        }

        // if any RB violations occurred
        if (!isYRed)
            deleteFixup(x);

        return true;
    }

    private void deleteFixup (Node x) {
        Node w;
        while ((x != root) && (x.red == BLACK)) {
            // is x a left child
            if (x == x.parent.left) {

                // w is x's sibling
                w = x.parent.right;

                // CASE 1
                if (w.red == true) {
                    w.red = BLACK;
                    x.parent.red = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // CASE 2
                if ((w.left.red == BLACK) && (w.right.red == BLACK)) {
                    w.red = RED;
                    x = x.parent;
                }

                else {
                    // CASE 3
                    if (w.right.red == BLACK) {
                        w.left.red = BLACK;
                        w.red = RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    // CASE 4
                    w.red = x.parent.red;
                    x.parent.red = BLACK;
                    w.right.red = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }

            else {
                w = x.parent.left;
                if (w.red == RED) {
                    w.red = BLACK;
                    x.parent.red = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                if ((w.right.red == BLACK) && (w.left.red == BLACK)) {
                    w.red = RED;
                    x = x.parent;
                }

                else {
                    if (w.left.red == BLACK) {
                        w.right.red = BLACK;
                        w.red = RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.left.red = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        x.red = BLACK;
    }

    
    @Override
    public String toString() {
        return print(root, "*", "");
    }

    private String print(Node node, String address, String child) {
        if (node == NIL)
            return "";
        
        String rep = "";
            
        String color = "";
        address = address + child;

        if (node.red)
            color = "red";
        else
            color = "black";

        rep += (address + ":" + color + ":" + node.key + "\n");
        rep += print(node.left, address, "L");
        rep += print(node.right, address, "R");
        return rep;
	}
}