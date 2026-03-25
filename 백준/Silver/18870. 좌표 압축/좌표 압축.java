
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int[] origin = new int[n];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            origin[i] = arr[i];
        }

        Arrays.sort(arr);

        List<Integer> list = new ArrayList<>();
        list.add(arr[0]);

        for (int j = 1; j < n; j++) {
            if (arr[j] != arr[j - 1]) {
                list.add(arr[j]);
            }
        }

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }

        for (int x : origin) {
            bw.write(map.get(x) + " ");
        }

        bw.flush();

        bw.close();
        br.close();
    }
}
