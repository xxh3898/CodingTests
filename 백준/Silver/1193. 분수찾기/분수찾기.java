
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int a = 1, b = 1;
        int line = 0;
        int sum = 0;
        int n = sc.nextInt();

        while (sum < n) {
            line++;
            sum += line;
        }

        int pos = n - (sum - line);

        if (line % 2 == 0) {
            a = pos;
            b = line - pos + 1;
        } else {
            a = line - pos + 1;
            b = pos;
        }

        System.out.print(a + "/" + b);
    }
}
