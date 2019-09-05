package gupao.netty.homeWork.oj8k;
/**选择排序：
 * 找到最值，放到首位，然后在剩余数据中循环处理
 * 可以同时找到最大最小值*/
public class I_Sort2_Select {
    private static final int arr[]={20,19,18,17,15,16,4,13,11,12,10,5,6,8,7,9,9,0,1,0,1,1};//22个数字[0]-[21]
    private static final int[] arr2={0,0,1,1,1,4,5,6,7,8,9,9,10,11,12,13,15,16,17,18,19,20};
    private static final int[] arr3={20,0,1,1,1,4,5,6,7,8,9,9,10,11,13,12,15,16,17,18,19,0};

    public static void main(String[] args) {
        sort_select_optimized(arr.clone());
        System.out.println("----------------------------------------s----------------\n\n");
        sort_select_optimized(arr2.clone());
        System.out.println("----------------------------------------s----------------\n\n");
        sort_select_optimized(arr3.clone());
    }

    private static void sort_select_optimized(int arr[]){
        printArr(arr);

        int len=arr.length;
        for(int i=0;i<len;i++){
            int val_min=arr[i],val_max=arr[i];
            int idx_min=i,idx_max=i;
            //找到最值
            for(int j=i;j<len;j++){
                if(arr[j]<val_min){
                    val_min=arr[j];
                    idx_min=j;
                }
                if(arr[j]>val_max){
                    val_max=arr[j];
                    idx_max=j;
                }
            }
            //进行交换 最小值放到i，最大值放到当前len-1
            System.out.println("找到最小值："+val_min+" 下标:"+idx_min+",最大值："+val_max+" 下标："+idx_max);

            int tmp_head=arr[i];//存放最小值前先保留
            int tmp_tail=arr[len-1];//存放最大值前先保留
            //先存放最值
            arr[i]=val_min;
            arr[len-1]=val_max;

            //查看原两端位置的值怎么处理
            //情况太复杂了不弄了

            len--;

            printArr(arr);
        }
        checkArr(arr2,arr);
    }

    private static void printArr(int[] arr){
        for(int a : arr)
            System.out.print(a+" " );
        System.out.println();
    }

    private static void checkArr(int[] arr,int[] arr_sorted){
        if(arr.length!=arr_sorted.length)
            System.out.println("排序有问题");
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=arr_sorted[i]){
                System.out.println("排序有问题");
                break;
            }
        }
    }

    private static void sort_select_origin(int arr[]){
        int len=arr.length;
        for(int i=0;i<len;i++){
            int val_min=arr[i];
            int idx_min=-1;
            //找到最值
            for(int j=i;j<len;j++){
                if(arr[j]<val_min){
                    val_min=arr[j];
                    idx_min=j;
                }
            }
            //进行交换
            if(i!=idx_min && idx_min!=-1){
                arr[idx_min]=arr[i];
                arr[i]=val_min;
            }
            printArr(arr);
        }
    }

}
