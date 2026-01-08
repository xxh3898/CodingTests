
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n1 = Integer.parseInt(br.readLine());
        int n2 = Integer.parseInt(br.readLine());
        int sum = 0;
        int min = 10001;

        for (int i = n1; i <= n2; i++) {
            int count = 0;

            for (int j = 1; j <= i; j++) {
                if (i % j == 0) {
                    count++;
                }
            }

            if (count == 2) {
                sum += i;

                if (i < min) {
                    min = i;
                }
            }
        }

        if (sum == 0) {
            bw.write(-1 + "");
        } else {
            bw.write(sum + "\n" + min);
        }

        bw.close();
        br.close();
    }
}
