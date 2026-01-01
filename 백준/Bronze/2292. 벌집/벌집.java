
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int box = 1;
        int count = 1;

        while (n > box) {
            box += 6 * count;
            count += 1;
        }

        System.out.println(count);
    }
}
