import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
/* Mirciu Andrei-Constantin 323CD */

public class P2 {
  private P2() {

  }

  static class Task {
    public static final String INPUT_FILE = "p2.in";
    public static final String OUTPUT_FILE = "p2.out";
    public static final int NDIRECTIONS = 4;
    // numarul de linii ale matricei
    int N;
    // numarul de coloane ale matricei
    int M;
    int K;
    // matricea in care citesc valorile
    int[][] matrix;
    int[][] visited;
    // coordonatele pe axa Ox
    private int[] ox = { -1, 1, 0, 0 };
    // coordonatele pe axa Oy
    private int[] oy = { 0, 0, -1, 1 };

    private void readInput() {
      try {
        MyScanner sc = new MyScanner(new FileInputStream(INPUT_FILE));
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        matrix = new int[N + 1][M + 1];
        visited = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
          for (int j = 1; j <= M; j++) {
            matrix[i][j] = sc.nextInt();
          }
        }

        sc.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void writeOutput(int result) {
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        StringBuilder sb = new StringBuilder();
        sb.append(result);
        bw.write(sb.toString());
        bw.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    // functie ce reseteaza matricea de vizitare
    private void resetVisited() {
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= M; j++) {
          visited[i][j] = 0;
        }
      }
    }

    private int determineArea(int min, int i, int j) {
      int ans = 1;
      int max = min + K;
      // marchez casuta din matrice ca fiind vizitata
      visited[i][j] = 1;
      // incerc toate casutele adiacente (stanga, dreapta, jos, sus)
      for (int index = 0; index < NDIRECTIONS; index++) {
        // aflu noile coordonate
        int a = i + ox[index];
        int b = j + oy[index];

        // verific sa nu ajung cu coordonatele in afara matricei
        if (a > N || b > M || a < 1 || b < 1) {
          continue;
        }
        // verific daca celula urmatoare poate fi inclusa in arie
        if (matrix[a][b] < min || matrix[a][b] > max) {
          continue;
        }
        // verific ca celula sa nu fie deja vizitata
        if (visited[a][b] == 1) {
          continue;
        }
        // daca toate conditiile au fost respectate, incrementez aria
        // si vizitez noua celula
        ans += determineArea(min, a, b);
      }
      return ans;
    }

    private int getResult() {
      // initializez rezultatul
      int result = 0;
      int i;
      int j;
      // variabila in care retin aria zonei gasite
      int calculate = 0;
      for (i = 1; i <= N; i++) {
        for (j = 1; j <= M; j++) {
          // initializez matricea visited
          resetVisited();
          // determin aria elementelor adiacente cu (i, j) aflate
          // in intervalul [matrix[i][j], matrix[i][j] + K]
          calculate = determineArea(matrix[i][j], i, j);
          // selectez aria maxima
          if (result < calculate) {
            result = calculate;
          }

        }
      }

      return result;
    }

    public void solve() {
      readInput();
      writeOutput(getResult());
    }

  }

  public static void main(String[] args) {
    new Task().solve();
  }
}
