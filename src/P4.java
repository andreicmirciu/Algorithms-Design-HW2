import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
/* Mirciu Andrei-Constantin 323CD */

public class P4 {
  private P4() {

  }

  static class Task {
    public static final String INPUT_FILE = "p4.in";
    public static final String OUTPUT_FILE = "p4.out";
    public static final int NMAX = 50005;

    // numarul de noduri din graf
    int N;
    // numarul de muchii din graf
    int M;
    // tipurile de muchii din graf
    int T;
    // vector in care citesc penalizarile
    int[] penalty;
    // matrice in care calculez distanta pana la un nod
    // tinand cont de tipul muchiei
    long[][] distance;

    // o muchie este tinuta ca o pereche (nod adiacent,
    // cost muchie, tip muchie)
    public class Edge {
      public Integer node;
      public long cost;
      public Integer type;

      public Edge(Integer node, long cost, Integer type) {
        this.node = node;
        this.cost = cost;
        this.type = type;
      }
    }

    @SuppressWarnings("unchecked")
    // reprezint graful ca un ArrayList<Edge>
    ArrayList<Edge>[] adj = new ArrayList[NMAX];

    private void readInput() {
      try {
        MyScanner sc = new MyScanner(new FileInputStream(INPUT_FILE));
        N = sc.nextInt();
        M = sc.nextInt();
        T = sc.nextInt();
        distance = new long[N + 1][T + 1];
        penalty = new int[T + 1];
        for (int i = 1; i <= N; i++) {
          adj[i] = new ArrayList<Edge>();
        }
        for (int i = 1; i <= M; i++) {
          int u;
          u = sc.nextInt();
          int v;
          v = sc.nextInt();
          int c;
          c = sc.nextInt();
          int t;
          t = sc.nextInt();
          // graful are muchii bidirectionale
          adj[u].add(new Edge(v, c, t));
          adj[v].add(new Edge(u, c, t));
        }
        for (int i = 1; i <= T; i++) {
          penalty[i] = sc.nextInt();
        }
        sc.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void writeOutput(long result) {
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        StringBuilder sb = new StringBuilder();
        // daca nu exista un drum de la nodul 1 la N,
        // afisez -1
        if (result >= Long.MAX_VALUE / 2) {
          sb.append(-1);
        } else {
          sb.append(result);
        }
        bw.write(sb.toString());
        bw.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void dijkstra(int source, int type) {
      // initializez o coada de prioritati cu elemente de tip Edge,
      // acestea fiind ordonate crescator in functie de costul muchiilor
      PriorityQueue<Edge> pq = new PriorityQueue<Edge>(N + 1, new Comparator<Edge>() {
        @Override
        public int compare(Edge a, Edge b) {
          Integer cost1 = (int) a.cost;
          Integer cost2 = (int) b.cost;
          return cost1.compareTo(cost2);
        }
      });
      // distanta pana la (source, type) este 0, fiind nodul de start
      distance[source][type] = 0;
      Edge firstEdge = new Edge(source, 0, type);
      // adaug nodul sursa in coada de prioritati
      pq.add(firstEdge);

      while (!pq.isEmpty()) {
        Edge myEdge = pq.poll();
        Integer u = myEdge.node;
        // trec mai departe daca drumul pana la nod are un cost mai mic
        // decat costul muchiei curente
        if (distance[u][myEdge.type] < myEdge.cost) {
          continue;
        }
        // stabilesc distanta pana la nodul u (aceasta distanta nu se mai schimba)
        distance[u][myEdge.type] = myEdge.cost;
        // parcurg toate nodurile adiacente lui u
        for (Edge edge : adj[u]) {
          Integer nod = edge.node;
          // stabilesc noul cost
          long newCost = myEdge.cost + edge.cost + penalty[edge.type];
          if (distance[nod][edge.type] < newCost) {
            continue;
          }
          pq.add(new Edge(edge.node, newCost, edge.type));
        }
      }
    }

    private long getResult() {
      // initializez rezultatul (costul minim al drumului
      // de la nodul 1 la nodul N)
      long result = Long.MAX_VALUE;
      int source = 1;

      // initializez matricea de distance de la un nod la altul
      for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= T; j++) {
          distance[i][j] = Long.MAX_VALUE / 2;
        }
      }

      // apelez algoritmul lui Dijkstra modificat pe nodul sursa
      dijkstra(source, 0);

      // aleg distanta minima pana la nodul N ce presupune
      // sa am cat mai putine muchii de tip diferit
      for (int i = 1; i <= T; i++) {
        result = Math.min(result, distance[N][i]);
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
