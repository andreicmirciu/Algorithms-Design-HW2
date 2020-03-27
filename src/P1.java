import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
/* Mirciu Andrei-Constantin 323CD */

public final class P1 {
  private P1() {

  }

  static class Task {
    public static final String INPUT_FILE = "p1.in";
    public static final String OUTPUT_FILE = "p1.out";
    // numarul de elemente din vector
    int N;
    // vectorul de distante
    int[] d;
    // vector folosit pentru a retine daca un nod a fost deja vizitat
    int[] visited;

    // muchie cu doua noduri
    public class Edge {
      public int node1;
      public int node2;
    }

    // o pereche ce retine un nod si distanta pana la acesta
    public class Pair {
      public int node;
      public int distance;

      public Pair() {

      }

      public Pair(int node, int distance) {
        this.node = node;
        this.distance = distance;
      }
    }

    private void readInput() {
      try {
        MyScanner sc = new MyScanner(new FileInputStream(INPUT_FILE));
        N = sc.nextInt();
        d = new int[N + 1];
        visited = new int[N + 1];
        for (int i = 1; i <= N; i++) {
          d[i] = sc.nextInt();
        }
        sc.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void writeOutput(ArrayList<Edge> result) {
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
        StringBuilder sb = new StringBuilder();
        // daca nu se poate construi graful, afisez -1
        if (result.size() == 1) {
          sb.append(result.get(0).node1);
        } else {
          // altfel afisez numarul de muchii si nodurile ce compun muchia
          sb.append(result.size()).append('\n');
          for (Edge edge : result) {
            sb.append(edge.node1).append(' ');
            sb.append(edge.node2).append('\n');
          }
        }

        bw.write(sb.toString());
        bw.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private ArrayList<Edge> getResult() {
      // reprezint graful ca un ArrayList<Edge>
      ArrayList<Edge> graph = new ArrayList<Edge>();
      // retin perechile (nod, distanta) intr-un ArrayList<Pair>
      ArrayList<Pair> pairs = new ArrayList<Pair>();
      int node1 = 0;
      int node2 = 0;
      int source = 1;
      int lastDistanceAdded = 0;
      int index = 0;
      int retine = 0;
      // adaug perechile (nod, distanta) in ArrayList
      for (int i = 1; i <= N; i++) {
        Pair pair = new Pair();
        pair.node = i;
        pair.distance = d[i];
        pairs.add(pair);
      }
      // sortez crescator vectorul de distante
      Arrays.sort(d);
      // sortez crescator ArrayList-ul de perechi
      Collections.sort(pairs, new Comparator<Pair>() {
        @Override
        public int compare(Pair a, Pair b) {
          Integer distance1 = a.distance;
          Integer distance2 = b.distance;
          return distance1.compareTo(distance2);
        }
      });

      for (int i = 1; i <= N; i++) {
        // verific daca e nodul sursa
        if (d[i] == 0) {
          // marchez nodul ca fiind vizitat
          visited[pairs.get(i - 1).node] = 1;
          continue;
          // verific daca nodul este vecin cu sursa
        } else if (d[i] == 1) {
          // creez o muchie noua
          Edge edge = new Edge();
          edge.node1 = source;
          edge.node2 = pairs.get(i - 1).node;
          // adaug muchia in graf
          graph.add(edge);
          // retin d[i]
          index = d[i];
          // marchez nodul ca fiind vizitat
          visited[pairs.get(i - 1).node] = 1;
          // ultima distanta adaugata in graf (fata de nodul sursa)
          lastDistanceAdded = d[i];
          // retin distanta
          retine = lastDistanceAdded;
        } else {
          // parcurg ArrayList-ul de perechi
          for (int k = 0; k < pairs.size(); k++) {
            // verific in ArrayList nodul care are distanta cautata si nu este deja
            // vizitat
            if (pairs.get(k).distance == d[i] && visited[pairs.get(i - 1).node] == 0) {
              // marchez nodul ca fiind vizitat
              visited[pairs.get(i - 1).node] = 1;
              // setez al doilea nod din muchia ce urmeaza sa fie adaugata
              node2 = pairs.get(i - 1).node;
              // ultima distanta adaugata in graf
              lastDistanceAdded = pairs.get(k).distance;
              // daca ultima distanta s-a modificat fata de cea anterioara, updatez
              // index
              if (retine != lastDistanceAdded) {
                index = k - 1;
                while (index >= 0 && lastDistanceAdded - pairs.get(index).distance != 1) {
                  index--;
                }
              }
              // retin distanta
              retine = lastDistanceAdded;
              // verific ca indexul sa fie valid si distanta pana la urmatorul nod ce
              // trebuie adaugat sa fie 1
              if (index >= 0 && lastDistanceAdded - pairs.get(index).distance == 1) {
                // caz de eroare
                if (index - 1 == graph.size()) {
                  ArrayList<Edge> error = new ArrayList<Edge>();
                  Edge edge = new Edge();
                  edge.node1 = -1;
                  edge.node2 = -1;
                  error.add(edge);
                  return error;
                  // altfel setez primul nod din muchia ce urmeaza sa fie adaugata
                } else {
                  node1 = graph.get(index - 1).node2;
                }
                // daca distanta este mai mare ca 1, ar mai trebui un nod
                // intermediar, deci graful nu se poate construi
              } else {
                ArrayList<Edge> error = new ArrayList<Edge>();
                Edge edge = new Edge();
                edge.node1 = -1;
                edge.node2 = -1;
                error.add(edge);
                return error;
              }
              break;
            }
          }
          Edge edge = new Edge();
          if (graph.size() == 0) {
            continue;
          }
          edge.node1 = node1;
          edge.node2 = node2;
          // adaug muchia in graf
          graph.add(edge);
        }
      }

      return graph;
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
