package test;

public class Solution {
    public ListNode reverseList(ListNode head) {
     if(head.next==null){
         return head;
     }else {
          ListNode first=reverseList(head.next);
          head.next.next=head;
          head.next=null;
          return first;
     }
    }
}
 class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
 }