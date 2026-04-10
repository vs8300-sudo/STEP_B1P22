import java.util.Arrays;

class TransactionLookup {
    public static int linearSearch(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) return i; // [cite: 127]
        }
        return -1;
    }

    public static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = target.compareTo(arr[mid]);
            if (res == 0) return mid; // [cite: 128]
            if (res > 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        String[] logs = {"accA", "accB", "accB", "accC"};
        Arrays.sort(logs); // Binary search requires sorted input [cite: 125]
        System.out.println("Binary search index for accB: " + binarySearch(logs, "accB"));
    }
}