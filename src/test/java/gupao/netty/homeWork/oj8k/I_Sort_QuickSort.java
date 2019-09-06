package gupao.netty.homeWork.oj8k;

import java.util.Arrays;
import java.util.Random;

/**快排*/
public class I_Sort_QuickSort {
        private static final int arr[]={20,19,18,17,15,16,4,13,11,12,10,5,6,7,8,9,9,0,1,0,1,1};//22个数字[0]-[21]
        private static final int[] arr2={0,0,1,1,1,4,5,6,7,8,9,9,10,11,12,13,15,16,17,18,19,20};
        private static final int[] arr3={20,0,1,1,1,4,5,6,7,8,9,9,10,11,13,12,15,16,17,18,19,0};

        private static final int[] aarr={3,2,4,1};
        public static void main(String[] args) {
//            sort_quick_partition(arr,0,arr.length-1);
//            checkArr(arr2,arr);
//            System.out.println("----------------------------------------s----------------\n\n");
//
////            sort_quick_partition(new int[]{0,0,0},0,2);
//
//            int[] arr2c=arr2.clone();
//            sort_quick_partition(arr2c,0,arr2c.length-1);
//            checkArr(arr2,arr2c);
//            System.out.println("----------------------------------------s----------------\n\n");
//
//            sort_quick_partition(arr3,0,arr3.length-1);
//            checkArr(arr2,arr2);
//            System.out.println("----------------------------------------s----------------\n\n");
            Random random=new Random(System.currentTimeMillis());
            for(int i=0;i<1000;i++){
                System.out.println("第"+i+"次随机数据生成并比较");
                int[] arr_random=getRandomArray(arr2,random);
                printArr(arr_random);
                int[] arr_random_sorted=Arrays.copyOfRange(arr_random,0,arr_random.length);
                sort_quick_partition(arr_random,0,arr_random.length-1);
                Arrays.sort(arr_random_sorted);
                if(!checkArr(arr_random_sorted,arr_random))
                    break;
            }
        }
        private static void sort_quick_partition(int[] arr,int start,int end){
            int i=start,j=end;
            if(i>=j){//只有一个元素
                return;
            }
            int standard=arr[i+(j-i)/2];

            for(;i<j;i++,j--){//这一段没有问题
                while(i<j && arr[i]<standard){//找到左边比标准大的下标
                    i++;
                }
                while(j>i && arr[j]>standard){//找到右边比标准小的下标
                    j--;
                }
                if(i<j){//交换
                    int tmp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=tmp;
                }
            }

            //经过上面的循环处理，得到i-左边都<=标准，j-右边都>标准  但是可能存在i>j的情况
            //根据i，j重新确定左右边界且不能相交
            int min=(i>j)?j:i,max=(i>j)?i:j;
            do {
                min++;
            }while(min<=end && arr[min]<standard) ;
            min--;
            do {
                max--;
            }while(max>=start && arr[max]>standard) ;
            max++;

            sort_quick_partition(arr,start,min);
            sort_quick_partition(arr,max,end);
        }

        private static void printArr(int[] arr){
            for(int a : arr)
                System.out.print(a+" " );
            System.out.println();
        }

        private static boolean checkArr(int[] arr,int[] arr_sorted){
            boolean isMatch=true;
            if(arr.length!=arr_sorted.length) {
                System.out.println("排序有问题");
                isMatch=false;
            }
            for(int i=0;i<arr.length;i++){
                if(arr[i]!=arr_sorted[i]){
                    System.out.println("排序有问题");
                    isMatch=false;
                    break;
                }
            }
            if(!isMatch){
                printArr(arr);
                printArr(arr_sorted);
                System.out.println("-----------------------------------");
            }
            printArr(arr);
            printArr(arr_sorted);
            return isMatch;
        }

        private static int[] getRandomArray(int[] arr_orgin,Random random){
            int len=arr_orgin.length;
            int[] arr_random=new int[len];
            int[] arr_index=new int[len];

//            for(int i=0;i<len;){//将arr_orgin的元素随机放到arr_random中
//                int ind=random.nextInt(len);
//                if(arr_index[ind]!=1){
//                    arr_index[ind]=1;
//                    arr_random[ind]=arr_orgin[i++];
//                }
//            }
            for(int i=0;i<len;){//将arr_orgin的元素随机放到arr_random中
                int value=random.nextInt(10);
                arr_random[i++]=value;
            }
            return arr_random;
        }
}
