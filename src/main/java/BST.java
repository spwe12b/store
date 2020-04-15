import java.util.*;

public class BST <E extends Comparable>{

    private class Node{
        E e;
        Node left;
        Node right;
        public Node(E e){
            this.e=e;
        }
    }
    private Node root;
    private int size;
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void add(E e){
      root=add(root,e);
    }
    //递归函数，在以node为根节点当中加入元素e
    //并返回加入新元素后的根节点
    private Node add(Node node,E e){
         if(node==null){
             size++;
             return new Node(e);
         }
         if(e.compareTo(node.e)<0){
             node.left=add(node.left,e);
         }else{
             node.right=add(node.right,e);
         }
         return node;
    }
    public boolean contains(E e){
         return contains(root,e);
    }
    private boolean contains(Node node,E e){
        if(node==null){
            return false;
        }
        if(e.compareTo(node.e)==0){
            return true;
        }else if(e.compareTo(node.e)<0){
            return contains(node.left,e);
        }else{
            return contains(node.right,e);
        }
    }
    public void  preOrder(){
         preOrder(root);
    }
    //前序遍历
    private void  preOrder(Node node){
           if(node==null){
               return;
           }
        System.out.println(node.e);
           preOrder(node.left);
           preOrder(node.right );
    }
    public void midOrder(){
       midOrder(root);
    }
    private void midOrder(Node node){
         if(node==null){
             return;
         }
         midOrder(node.left);
        System.out.println(node.e);
        midOrder(node.right);
    }
    public void lastOrder(){
        lastOrder(root);
    }
    private void lastOrder(Node node){
        if(node==null){
            return;
        }
        lastOrder(node.left);
        lastOrder(node.right);
        System.out.println(node.e);
    }
    public void ceng(){
           ceng(root);
    }
    private void ceng(Node node){
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node cur=queue.peek();
            if(cur.right!=null){
                queue.add(cur.right);
            }
            if(cur.left!=null){
                queue.add(cur.left);
            }
            System.out.println(queue.poll().e);
        }

    }



    public static void main(String[] args) {
        BST bst=new BST();
        bst.add(5);
        bst.add(3);
        bst.add(6);
        bst.add(7);
        bst.add(2);
        bst.add(4);
        //bst.lastOrder();
        bst.ceng();
    }
}
