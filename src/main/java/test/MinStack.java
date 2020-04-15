package test;

    class MinStack {
        private Node node;//对外开发的栈
        private Node paiNode;//维护的排序的栈，由小到大，最小元素就是栈顶元素
        private class Node{//节点类
            private int e;
            private Node next;
            private Node(int e){
                this.e=e;
            }
        }
        public MinStack() {
        }
        //排序栈的压入
        private void paiPush(int x,Node node){
            if(node.next==null){//当排序栈的最小一个元素都比x小时就将x压入栈底
                node.next=new Node(x);
                return;
            }
            if(x<=node.next.e){//当x找到插入位置时
                Node next=node.next;
                Node cur=new Node(x);
                node.next=cur;
                cur.next=next;
                return;
            }else{
                //进行递归
                paiPush(x,node.next);
            }
        }
        public void push(int x) {//对外开放的栈压入
            if(node==null){//当普通栈为空，排序栈肯定也为空
                paiNode=new Node(x);
                node=new Node(x);
                return;
            }
            if(x<=paiNode.e){
                Node next=paiNode;
                Node min=new Node(x);
                paiNode=min;
                min.next=next;
            }else{
                paiPush(x,paiNode);
            }
            //普通栈的压入操作；
            Node next=node;
            Node cur=new Node(x);
            node=cur;
            cur.next=next;
        }
        //排序栈的弹出操作
        private void paiPop(int x,Node node){
            if(node.next.e==x){//不用担心node.next为空,因为排序栈和普通栈放的是同样的元素
                node.next=node.next.next;
                return;
            }
            //递归操作
            paiPop(x,node.next);
        }
        public void pop() {//普通栈的弹出
            int x=node.e;
            node=node.next;
            //排序栈的弹出
            if(paiNode.e==x){
                paiNode=paiNode.next;
            }else{
                paiPop(x,paiNode);
            }
        }
        //查看普通栈的栈顶元素
        public int top() {
            return node.e;
        }
        //获取最小值
        public int getMin() {
            return paiNode.e;//直接返回排序栈的栈顶元素
        }
    }

