
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw;
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        boolean[] check = new boolean[42];

        for (int i = 0; i < 10; i++) {
            int num = Integer.parseInt(br.readLine());

            check[num % 42] = true;
        }

        int totalCount = 0;
        for (boolean b : check) {
            if (b) {
                totalCount++;
            }
        }

        bw.write(String.valueOf(totalCount));
        bw.flush();
        bw.close();
        br.close();
    }
}
