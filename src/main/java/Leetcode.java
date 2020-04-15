

import java.nio.Buffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class Leetcode {
}



class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
}
class BTree{
    TreeNode root;
    public void add(int val){
       root=add(root,val);
    }
    private TreeNode add(TreeNode treeNode,int val){
        if(treeNode==null) {
            return new TreeNode(val);
        }
        if(val>treeNode.val)
            treeNode.left=add(treeNode.left,val);
        if(val<treeNode.val)
            treeNode.right=add(treeNode.right,val);
        return treeNode;
    }
    public void sout(){
        sout(root);
    }
    private void sout(TreeNode treeNode){
        if(treeNode==null){
            return;
        }
        sout(treeNode.left);
        System.out.println(treeNode.val);
        sout(treeNode.right);
    }


}

class ListNode {
     int val;
      ListNode next;
      ListNode(int x) {
         val = x;
         next = null;
     }
     public static ListNode add(ListNode node,ListNode addNode){
          ListNode cur=node;
          while(cur.next!=null){
              cur=cur.next;
          }
          cur.next=addNode;
          return node;
     }
}


class Solution {


    public static ListNode deleteNode(ListNode head, int val) {
       ListNode dummyHead=new ListNode(0);
       dummyHead.next=head;
       while(dummyHead.next!=null){
           if(dummyHead.next.val==val){
               ListNode next=dummyHead.next.next;
               dummyHead.next.next=null;
               dummyHead.next=next;
           }
           dummyHead=dummyHead.next;
       }
       return head;
    }



      public static int partition(int[] nums,int l,int r,int target,int i){
            int mid=nums[(l+r)/2]+nums[i];
            if(l>r){
                return 0;
            }
            if(l==r){
                if(target==nums[l]+nums[i]){
                    return l;
                }else{
                    return 0;
                }
            }
            if(target>mid){
                int count=partition(nums,(l+r)/2+1,r,target,i);
                if(count!=0){
                    return count;
                }
            }
            else if(target<mid){
                int count=partition(nums,l,(l+r)/2-1,target,i);
                if(count!=0){
                    return count;
                }
            }else {
                 return (l+r)/2;
            }
            return 0;
        }


    public static void main(String[] args) {
        ListNode node=new ListNode(-3);
        ListNode.add(node,new ListNode(-5));
        ListNode.add(node,new ListNode(-99));
        Solution.deleteNode(node,-3);
    }
}




