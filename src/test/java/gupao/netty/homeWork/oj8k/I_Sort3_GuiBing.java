package gupao.netty.homeWork.oj8k;

import java.util.Arrays;

public class I_Sort3_GuiBing {
    private static final int arr[]={20,19,18,17,15,16,4,13,11,12,10,5,6,8,7,9,9,0,1,0,1,1};//22个数字[0]-[21]
    private static final int[] arr2={0,0,1,1,1,4,5,6,7,8,9,9,10,11,12,13,15,16,17,18,19,20};
    private static final int[] arr3={20,0,1,1,1,4,5,6,7,8,9,9,10,11,13,12,15,16,17,18,19,0};

    private static final int[] aarr={3,2,4,1};
    public static void main(String[] args) {
        int[] arr_c,arr2_c,arr3_c;


        arr_c=sort_fenzhi(arr);
        checkArr(arr2,arr_c);
        System.out.println("----------------------------------------s----------------\n\n");

        arr2_c=sort_fenzhi(arr2);
        checkArr(arr2,arr2_c);

        System.out.println("----------------------------------------s----------------\n\n");
        arr3_c=sort_fenzhi(arr3);
        checkArr(arr2,arr3_c);

        sort_fenzhi(aarr);
        printArr(aarr);

    }
    //分治
    public static int[] sort_fenzhi(int[] arr){
        int len=arr.length;
        if(arr.length<2){//实际上数量小于一定数量可以选择其他排序方法
            return  arr;
        }
        return sort_guibing(sort_fenzhi(Arrays.copyOfRange(arr,0,len/2)), sort_fenzhi(Arrays.copyOfRange(arr,len/2,len)));
    }
    //归并
    private static int[] sort_guibing(int[] left, int[] right) {
        int[] result=new int[left.length+right.length];
        int llength=left.length,rlength=right.length;

        for(int index=0,i=0,j=0;index<result.length;index++){
            if(i>=llength){//左边数组短，已遍历完
                result[index]=right[j++];
            }else if(j >= rlength){
                result[index]=left[i++];
            }else if(left[i]<right[j]){
                result[index]=left[i++];
            }else {
                result[index]=right[j++];
            }
        }

        return result;
    }


    private static void printArr(int[] arr){
        for(int a : arr)
            System.out.print(a+" " );
        System.out.println();
    }

    private static void checkArr(int[] arr,int[] arr_sorted){
        printArr(arr);
        printArr(arr_sorted);
        if(arr.length!=arr_sorted.length)
            System.out.println("排序有问题");
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=arr_sorted[i]){
                System.out.println("排序有问题");
                break;
            }
        }
    }

    public static int[] MergeSort(int[] array) {
        if (array.length < 2) return array;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }
    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        return result;
    }
}
