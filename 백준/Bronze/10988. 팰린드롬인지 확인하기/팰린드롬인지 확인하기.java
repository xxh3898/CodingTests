
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String str = br.readLine();
        String revesStr="";

         for (int i = str.length() - 1; i >= 0; i--) {            
            revesStr += str.charAt(i);        
        }

        if (str.equals(revesStr)) {
            bw.write(1+"");
        }else{
            bw.write(0+"");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
