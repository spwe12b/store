import com.github.pagehelper.PageHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Sort {
    public static void main(String[] args) throws Exception{
        int[] ints=array(1000000,0,100000);
        int[] ints1=copyArray(ints);
        int[] ints2=copyArray(ints);
        int[] ints3=copyArray(ints);
        //testSort(ints,"selectSort");
        //testSort(ints1,"insertSort");
       // testSort(ints2,"mergeSort");
      testSort(ints3,"Sort","quickSort");
    }
    public static void quickSort(int[] ints){
        quickSort(ints,0,ints.length-1);
    }
    public static void quickSort(int[] ints,int l,int r){
        if((r-l)<70){
            insertSort2(ints,l,r);
            return;
        }
        //int p=partition2(ints,l,r);
        //partition3
        int j=(int)Math.random()*(r-l+1)+l;
        swap(ints,j,l);
        int v=ints[l];//要插入的元素
        int lt=l;//ints[l+1>>lt]<v
        int gt=r+1;//ints[gt>>r]>v
        int i=l+1;//ints[lt+1>>i)=v,i是指针
        while(i<gt){
            if(ints[i]<v){
                swap(ints,i,lt+1);
                lt++;
                i++;
            }else if(ints[i]>v){
                swap(ints,i,gt-1);
                gt--;
            }else{
                i++;
            }
        }
        swap(ints,l,lt);
        quickSort(ints,l,lt-1);
        quickSort(ints,gt,r);
    }
    public static int partition(int[] ints,int l,int r) {
        int j=(int)Math.random()*(r-l+1)+l;
        swap(ints,j,l);
        int v=ints[l];//要插入的元素
        int p=l;//最终的分割点
        for(int i=l+1;i<=r;i++){
            if(ints[i]<v){
                swap(ints,p+1,i);
                p++;
            }
        }
        swap(ints,l,p);
        return p;
    }
    public static int partition2(int[] ints,int l,int r) {
        int j=(int)(Math.random()*(r-l+1)+l);
        swap(ints,j,l);
        int v=ints[l];//要插入的元素
        int i=l+1;//从左边开始走的指针
        int k=r;//从右边开始走的指针
        while(true){
            while(i<=r&&ints[i]<v){
                i++;
            }
            while(k>=l+1&&ints[k]>v){
                k--;
            }
            if(i>=k){
                break;
            }
            swap(ints,i,k);
            i++;
            k--;
        }
        swap(ints,l,k);
        return k;
    }
    public static void mergeSort(int[] ints){
       mergeSort(ints,0,ints.length-1);
    }
    //对ints[l>>mid]和ints[mid+1,r]进行归并，两个部分都已经排好序的
    public static void merge(int[] ints,int l,int mid,int r){
        int[] tmpInts=Arrays.copyOfRange(ints, l, r+1);
        int leftFirst=l;//左数组的起始位置
        int rightFirst=mid+1;//右数组的起始位置
        for(int k=l;k<=r;k++){
            if(leftFirst>mid){
                ints[k]=tmpInts[rightFirst-l];
                rightFirst++;
            }else if(rightFirst>r) {
                ints[k]=tmpInts[leftFirst-l];
                leftFirst++;
            }
            else if(tmpInts[leftFirst-l]<tmpInts[rightFirst-l]){
                ints[k]=tmpInts[leftFirst-l];
                leftFirst++;
            }else{
                ints[k]=tmpInts[rightFirst-l];
                rightFirst++;
            }
        }
    }
    //对arr[l>>>r]进行归并排序
    private static void mergeSort(int[] ints,int l,int r){
      if(r-l<70){
          insertSort2(ints,l,r);
          return;
      }
        //核心就是将mid的值不断变小，用mid来分割左右两部分
        int mid=(l+r)/2;
        mergeSort(ints,l,mid);
        mergeSort(ints,mid+1,r);
        if(ints[mid]>ints[mid+1]) {
            merge(ints, l, mid, r);
        }
    }
    public static void insertSort(int[] ints){
        for(int i=1;i<ints.length;i++){
            for(int j=i;j>0;j--){
                if(ints[j]<ints[j-1]){
                    swap(ints,j,j-1);
                }else{
                    break;
                }
            }
        }

    }
    public static void insertSort2(int[] ints){
        for(int i=1;i<ints.length;i++){
            int cur=ints[i];
            int j=i;
            for(j=i;j>0;j--){
                if(cur<ints[j-1]){
                    ints[j]=ints[j-1];
                }else{
                    break;
                }
            }
            ints[j]=cur;
        }
    }
    public static void insertSort2(int[] ints,int l,int r){
        for(int i=l;i<=r;i++){
            int cur=ints[i];
            int j=i;
            for(j=i;j>l;j--){
                if(cur<ints[j-1]){
                    ints[j]=ints[j-1];
                }else{
                    break;
                }
            }
            ints[j]=cur;
        }
    }
    public static void selectSort(int[] ints){
         for(int i=0;i<ints.length;i++){
             int mid=i;
             for(int a=i+1;a<ints.length;a++){
                 if(ints[a]<ints[mid]){
                     mid=a;
                 }
             }
             swap(ints,i,mid);
         }
    }
    public static void swap(int[] ints,int i,int j){
        int tmp=ints[i];
        ints[i]=ints[j];
        ints[j]=tmp;
    }
    public static int[] array(int n,int rangL,int rangR){
          int[] ints=new int[n];
          for(int i=0;i<n;i++){
              ints[i]=(int)(Math.random()*(rangR-rangL+1)+rangL);
          }
       return ints;
    }
    public static int[] copyArray(int[] ints){
        int[] newInts=new int[ints.length];
        for(int i=0;i<ints.length;i++){
            newInts[i]=ints[i];
        }
        return newInts;
    }
    public static void testSort(int[] ints,String className,String methodName)throws Exception{
        long start=System.currentTimeMillis();
        Class c=Class.forName(className);
        Method method=c.getMethod(methodName,int[].class);
        method.invoke(null,ints);
        long end=System.currentTimeMillis();
        if(!isSorted(ints)){
            System.out.println("数组未排序");
            return;
        }
        //print(ints);
        System.out.println(methodName+": "+(end-start)+"毫秒");
    }
    public static boolean isSorted(int[] ints){
        for(int i=0;i<ints.length-1;i++){
            if(ints[i]>ints[i+1]){
                return false;
            }
        }
        return true;
    }
    private static void print(int[] ints){
        for(int i=0;i<ints.length;i++){
            System.out.println(ints[i]);
        }
    }
}
