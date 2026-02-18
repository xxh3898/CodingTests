
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
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        int[] arr = new int[n];
        long result = 0;
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    long tempSum = arr[i] + arr[j] + arr[k];

                    if (tempSum == m) {
                        result = tempSum;
                        break;
                    }

                    if (tempSum < m && result < tempSum) {
                        result = tempSum;
                    }
                }
                if (result == m) {
                    break;
                }
            }
            if (result == m) {
                break;
            }
        }
        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }
}
