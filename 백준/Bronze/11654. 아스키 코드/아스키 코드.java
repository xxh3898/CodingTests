import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
        char str = br.readLine().charAt(0);
        int S = str;
		
        bw.write(String.valueOf(S));
		
        bw.flush();
        bw.close();
        br.close();
    }
}