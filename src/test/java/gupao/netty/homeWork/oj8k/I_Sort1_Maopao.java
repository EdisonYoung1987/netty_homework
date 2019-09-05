package gupao.netty.homeWork.oj8k;
/**冒泡排序：
 * 循环遍历，两两交换，依次放到数组末尾
 * 可以根据最后交换位置，减少最外层循环次数*/
public class I_Sort1_Maopao {
    private static final int arr[]={20,19,18,17,15,16,4,13,11,12,10,5,6,8,7,9,9,0,1,0,1,1};//22个数字[0]-[21]
    private static final int[] arr2={0,0,1,1,1,4,5,6,7,8,9,9,10,11,12,13,15,16,17,18,19,20};
    private static final int[] arr3={20,0,1,1,1,4,5,6,7,8,9,9,10,11,13,12,15,16,17,18,19,0};

    private static void printArr(int[] arr){
        for(int a : arr)
            System.out.print(a+" " );
        System.out.println();
    }

    public static void main(String[] args) {
        sort_maopao_origin(arr2.clone());
        System.out.println("----------------------------------------s----------------\n\n");
        sort_maopao_optimized(arr2.clone());
    }
    private static void sort_maopao_origin(int arr[]){
        int len=arr.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int tmp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=tmp;
                }
            }
            printArr(arr);
        }
    }
    private static void sort_maopao_optimized(int arr[]){
        printArr(arr);

        int len=arr.length;
        int last=len;
        for(int i=0;last>0;i++){
            int last_bak=last;
            for(int j=0;j<len-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int tmp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=tmp;
                    last=j+1;

                }
//                printArr(arr);
            }
            if(last_bak==last){//未进行交换
                last=0;
            }
//            System.out.println("last="+last);
            printArr(arr);
        }
    }
}
