
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] arr = new int[n];
        int c = 0;
        sc.nextLine();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        for (int j = 0; j < m; j++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            sc.nextLine();
            c = arr[a - 1];
            arr[a - 1] = arr[b - 1];
            arr[b - 1] = c;
        }

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
