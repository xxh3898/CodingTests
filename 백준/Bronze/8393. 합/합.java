
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int i = 0, sum = 0;
        while (i < n) {
            i++;
            sum = i + sum;
        }
        System.out.println(sum);
    }
}
