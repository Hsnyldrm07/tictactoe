(ns ttt.view)

(defn- render-blanks
  "Biorąc pod uwagę tablicę łańcuchów i wartości zerowych,
  zwraca '_' zamiast wartości nils i zwraca ciągi niezmienione"
  [strings-and-nils]
  (map #(if (nil? %) "_" %) strings-and-nils))

(defn print-board
  [board]
  (let [rows (map render-blanks board)]
    (println)
    (doall (map #(apply println %) rows))
    (println)))

(defn print-introduction
  [players]
  (println "-----------")
  (println "TIC TAC TOE")
  (println "-----------")
  (println "Gracze" (apply str (interpose " i " players)) "gotowy")
  (println "Aby umieścić figurę, wprowadź liczbę od 1 do N,")
  (println "Gdzie N to całkowita liczba miejsc na planszy"))
