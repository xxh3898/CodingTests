import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
 
public class Main {
 
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		int A = in.nextInt();
		int B = in.nextInt();
 
		in.close();
 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
 
		bw.write((A+B) + "\n");
		bw.write((A-B) + "\n");
		bw.write((A*B) + "\n");
		bw.write((A/B) + "\n");
		bw.write((A%B) + "\n");
 
		bw.flush();
		bw.close();
	}
}