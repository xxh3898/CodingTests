import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] arr = new int[n];
        
        for (int j = 0; j < m; j++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            while (a <= b) {
                arr[a - 1] = c;
                a++;
            }
        }
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
