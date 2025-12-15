
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] arr = new int[28];
        int[] outArr = new int[2];
        int n = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        for (int j = 1; j <= 30; j++) {
            int count = 0;

            for (int k = 0; k < arr.length; k++) {
                if (arr[k] == j) {
                    count = 1;
                }
            }
            if (count == 0) {
                outArr[n] = j;
                n++;
            }
        }
        bw.write(outArr[0] + " " + outArr[1]);
        bw.flush();
        bw.close();
        br.close();
    }
}
