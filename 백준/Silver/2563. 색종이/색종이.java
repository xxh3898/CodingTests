
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] arr = new int[100][100];
        int count = 0;

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            for (int k = a - 1; k < a + 9; k++) {
                for (int j = b - 1; j < b + 9; j++) {
                    arr[k][j] = 1;
                }
            }
        }

        for (int m = 0; m < arr.length; m++) {
            for (int l = 0; l < arr.length; l++) {
                if (arr[m][l] == 1) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }
}
