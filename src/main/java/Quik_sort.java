public class Quik_sort {
    public static void main(String[] args) throws Exception {
        int[] ints = Sort.array(1000000, 0, 100000);
        Sort.testSort(ints, "Quik_sort", "quikSort");
    }

    public static void quikSort(int[] ints) {
        quikSort(ints, 0, ints.length - 1);
    }

    private static void quikSort(int[] ints, int l, int r) {
        if (l >= r) {
            return;
        }
        //int j = partition(ints, l, r);
        //partition3
        //ints[l+1>>i]<ints[l],ints[j>>r]>ints[l],ints[i+1>>p)=ints[l]
        int v = (int) (Math.random() * (r - l + 1) + l);
        Sort.swap(ints, l, v);
        int i=l;//小于V
        int j=r+1;//大于V
        int p=l+1;
        while(p<j){
            if(ints[p]<ints[l]){
                Sort.swap(ints,p,i+1);
                i++;
                p++;
            }else if(ints[p]>ints[l]){
                Sort.swap(ints,p,j-1);
                j--;
            }else{
                p++;
            }
        }
        Sort.swap(ints,l,i);
        i--;
        quikSort(ints, l, i );
        quikSort(ints, j, r);
    }

    private static int partition(int[] ints, int l, int r) {
        int v = (int) (Math.random() * (r - l + 1) + l);
        Sort.swap(ints, l, v);
        int lt = l+1;//左边界
        int rt = r ;//右边界
        //ints[l+1>>lt)<ints[v],ints(rt>>r]>ints[v]
        while (lt < rt) {
            while (rt > l  && ints[rt] > ints[l]) rt--;
            while (lt <= r && ints[lt] < ints[l]) lt++;
            Sort.swap(ints, rt, lt);
            rt--;
            lt++;
        }
        Sort.swap(ints, l, lt);
        return lt;
    }
}
