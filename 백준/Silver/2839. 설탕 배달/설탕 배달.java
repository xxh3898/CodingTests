
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int result = 0;
        int count = 0;

        while (n >= 0) {
            if (n % 5 == 0) {
                result = n / 5;
                System.out.println(result + count);
                return;
            } else {
                n = n - 3;
                count++;
            }
        }
        System.out.println(-1);
        sc.close();
    }
}
