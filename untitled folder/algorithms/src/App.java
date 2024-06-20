public class App {
    public static void main(String[] args) throws Exception {
        int[] array = {2,1,5,1,3,2};

        int size = 3;

        int result = f(array,size);

        System.out.println(result);
    }

    private static int f(int[] array, int size) {

       int windowSum = 0;

       int maxSum = Integer.MIN_VALUE;

        for(int i=0;i<size;i++){

            windowSum += array[i];

        }

        maxSum = windowSum;

        for(int i = size; i < array.length;i++){

            windowSum += array[i] - array[i-size];

            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;

    }


}
