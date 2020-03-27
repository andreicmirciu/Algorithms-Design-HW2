import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class MyScanner {
  private BufferedReader br;
  private StringTokenizer st;

  MyScanner(FileInputStream f) {
    br = new BufferedReader(new InputStreamReader(f));
  }

  String next() throws IOException {
    while (st == null || !st.hasMoreElements()) {
      st = new StringTokenizer(br.readLine());
    }
    return st.nextToken();
  }

  int nextInt() throws IOException {
    return Integer.parseInt(next());
  }

  void close() throws IOException {
    br.close();
  }
}
