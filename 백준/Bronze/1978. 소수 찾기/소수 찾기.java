
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int sum = 0;

        for (int i = 0; i < n; i++) {
            int count = 0;
            int num = Integer.parseInt(st.nextToken());

            for (int j = 1; j <= num; j++) {
                if (num % j == 0) {
                    count++;
                }
            }

            if (count == 2) {
                sum++;
            }
        }
        bw.write(sum + "");

        bw.close();
        br.close();
    }
}
