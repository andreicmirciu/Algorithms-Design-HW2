# Algorithms-Design-HW2
Solved several algorithmic problems on graphs.

###### Problema 1:
	Am ales sa reprezint graful ca un ArrayList<Edge>, unde Edge este o clasa
ce contine cele doua noduri aferente unei muchii. Ideea principala de rezolvare
a exercitiului presupune sortarea crescatoare a vectorului de distante oferit,
dupa care verific daca valoarea de pe pozitia i este mai mica cu 1 sau egala cu
valoarea de pe pozitia i + 1 (diferenta dintre distantele de la pozitiile i + 1
si i trebuie sa fie cel mult egala cu 1), pentru a putea adauga o muchie noua.
Daca diferenta dintre distante este mai mare de 1, inseamna ca in graf ar 
trebui sa mai existe un nod suplimentar care sa uneasca celelalte doua noduri
cu cate o muchie, deci graful nu se poate construi si returnez -1. Deoarece 
cand sortez vectorul de distante pierd informatia despre nodul corespunzator,
retin aceste informatii intr-un ArrayList suplimentar de perechi de forma 
(nod, distanta). In continuare, parcurg fiecare element din vectorul de 
distante si verific valorile. Daca distanta este 0, inseamna ca sunt la nodul
sursa si nu pot adauga o muchie. Daca distanta este 1, inseamna ca nodul este
adiacent nodului sursa. In celelalte cazuri, adaug o muchie de la nodul curent
la nodul cu prima distanta mai mica cu 1 fata de distanta pana la nodul curent.
Pentru a sti exact intre ce noduri trebuie sa adaug o muchie in graf, parcurg
ArrayList-ul de perechi pana gasesc nodul cu distanta cautata (acesta va
reprezenta nodul 2), iar pentru nodul 1 ma folosesc de un nod deja pus in graf.
Complexitatea algoritmului descris este O(N^2) in cazul cel mai defavorabil, 
unde N reprezinta numarul de noduri din graf.
###### Problema 2:
	Pentru a putea calcula aria maxima din matrice (zona cu cele mai multe
casute vecine care respecta conditia ca diferenta dintre valorea maxima si
valoarea minima sa fie cel mult K), ma folosesc de o matrice visited in care
verific daca am trecut deja printr-o anumita casuta. Astfel, parcurg matricea
pe fiecare linie si coloana si calculez de fiecare data zona cu cele mai multe
casute care respecta conditia, selectand mereu drept rezultat aria cea mai 
mare. In ceea ce priveste calculul ariei, ma deplasez de fiecare data din 
casuta in care ma aflu in toate cele patru directii (stanga, dreapta, jos,
sus). Dupa mutarea intr-o noua casuta, verific sa nu ajung la indecsi invalizi
din matrice (ajung cu coordonatele in afara matricei), verific daca casuta se
incadreaza in limita de diferenta de valoare de maxim K si nu in ultimul rand
sa nu fi fost deja vizitata. In cazul in care celula trece cu succes de aceste
verificari, aceasta este adaugata in calculul ariei. Mereu cand trec la 
calculul ariei pentru o noua celula, am grija sa resetez matricea visited.
Complexitatea algoritmului este O(N^2 * M^2) in cazul cel mai defavorabil,
unde N reprezinta numarul de linii, respectiv M numarul de coloane din matrice.

###### Problema 3:
	Pentru calcularea costului minim al unui drum de la nodul 1 la nodul N,
m-am folosit de algoritmul lui Dijkstra (nu exista muchii de cost negativ),
pe care l-am modificat astfel incat sa se tina cont de penalizarile adaugate
pentru cazul in care muchiile sunt de tipuri diferite. In rezolvarea problemei,
ma folosesc de o matrice distance in care coordonatele reprezinta nodul si 
tipul muchiei. Astfel, retin pentru fiecare nod cea mai mica penalizare ce se
poate aplica pe distanta minima pana la acel nod de la nodul sursa. De 
asemenea, graful este reprezentat sub forma de lista de adiacenta. Initializez
matricea distance cu valori mari (Long.MAX_VALUE / 2), urmand ca la final sa
selectez drept rezultat distanta pana la nodul N cu cea mai mica penalizare.
Algoritmul lui Dijkstra presupune folosirea unei cozi de prioritate, in care
nodurile se afla ordonate crescator dupa distanta pana la nodul sursa.
Complexitatea algoritmului este de aproximativ O(NlogN + MlogN), unde N 
reprezinta numarul de noduri, respectiv M numarul de muchii din graf.

###### Problema 4:

	Am folosit acelasi cod ca la problema 3.
	Mentionez ca am folosit scheletul de laborator pentru structurarea 
fisierelor .java si rezolvarea problemelor, respectiv clasa MyScanner
oferita, pentru o citire mai rapida a datelor de intrare.
