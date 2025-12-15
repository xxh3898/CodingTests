
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

        String num = "";
        int[] arr = {1, 1, 2, 2, 2, 8};

        for (int i = 0; i < 6; i++) {
            num += arr[i] - Integer.parseInt(st.nextToken());
            bw.write(num + " ");
            num = "";
        }

        bw.close();
        br.close();
    }
}
