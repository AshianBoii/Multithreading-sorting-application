import java.util.Arrays;

public class MultithreadedSorting {
    
    // Global arrays for unsorted and sorted lists
    static int[] unsortedList = {7, 12, 19, 3, 18, 4, 2, 6, 15, 8};
    static int[] sortedList = new int[unsortedList.length];  // To store the final merged sorted list
    
    public static void main(String[] args) throws InterruptedException {
        // Find the mid-point of the array
        int mid = unsortedList.length / 2;
        
        // Create sorting threads for each half of the list
        Thread sortingThread1 = new Thread(new Sorter(0, mid));
        Thread sortingThread2 = new Thread(new Sorter(mid, unsortedList.length));
        
        // Start the sorting threads
        sortingThread1.start();
        sortingThread2.start();
        
        // Wait for sorting threads to complete
        sortingThread1.join();
        sortingThread2.join();
        
        // After sorting is done, merge the two sorted halves using a merging thread
        Thread mergeThread = new Thread(new Merger(0, mid, unsortedList.length));
        mergeThread.start();
        
        // Wait for the merging thread to complete
        mergeThread.join();
        
        // Display the sorted list
        System.out.println("Sorted List: " + Arrays.toString(sortedList));
    }
    
    // Sorter class that sorts a part of the list using Bubble Sort
    static class Sorter implements Runnable {
        int start, end;
        
        Sorter(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public void run() {
            bubbleSort(unsortedList, start, end);
        }
        
        // Simple bubble sort implementation
        private void bubbleSort(int[] arr, int start, int end) {
            for (int i = start; i < end - 1; i++) {
                for (int j = start; j < end - 1 - (i - start); j++) {
                    if (arr[j] > arr[j + 1]) {
                        // Swap elements
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }
    
    // Merger class that merges two sorted parts of the array
    static class Merger implements Runnable {
        int start, mid, end;
        
        Merger(int start, int mid, int end) {
            this.start = start;
            this.mid = mid;
            this.end = end;
        }
        
        @Override
        public void run() {
            merge(unsortedList, start, mid, end, sortedList);
        }
        
        // Merge function to merge two sorted sublists into the sortedList array
        private void merge(int[] arr, int start, int mid, int end, int[] result) {
            int i = start, j = mid, k = start;
            
            // Merge the two halves
            while (i < mid && j < end) {
                if (arr[i] <= arr[j]) {
                    result[k++] = arr[i++];
                } else {
                    result[k++] = arr[j++];
                }
            }
            
            // Copy the remaining elements from the first half
            while (i < mid) {
                result[k++] = arr[i++];
            }
            
            // Copy the remaining elements from the second half
            while (j < end) {
                result[k++] = arr[j++];
            }
        }
    }
}
