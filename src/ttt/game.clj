(ns ttt.game)

(defn- winning-runs
  "Biorąc pod uwagę sekwencję n-elementowych sekwencji, zwróć te,
  które są całkowicie wypełnione danym symbolem"
  [runs symbol]
  (filter #(apply = symbol %) runs))

(defn- horizontal-winner?
  [board player]
  (let [winners (winning-runs board player)]
    (some? (seq winners))))

(defn- vertical-winner?
  [board player]
  (let [cols (apply map vector board)] ;; obróć tablicę o 90 stopni
    (horizontal-winner? cols player)))

(defn- diagonal-winner?
  [board player]
  (let [size (count (first board))
        diag-1 (apply map vector [(range size) (range size)])
        diag-2 (apply map vector [(reverse (range size)) (range size)])
        d1 (map #(get-in board %) diag-1)
        d2 (map #(get-in board %) diag-2)]
    (some? (seq (winning-runs [d1 d2] player)))))

(defn- any-winners?
  [board player]
  (or (diagonal-winner? board player)
      (vertical-winner? board player)
      (horizontal-winner? board player)))

(defn- full?
  [board]
  (every? #(not= nil %) (flatten board)))

(defn- integer-to-coords
  "Mając macierz, zwróć współrzędne nth szczeliny"
  [matrix n]
  (let [matrix-row-length (count (first matrix))]
    ((juxt quot rem) n matrix-row-length)))

(defn place-piece
  "Biorąc pod uwagę planszę, figurę i całkowity indeks na szachownicy od 1 do
  board-max, spróbuj umieścić bierkę na planszy. Zwróć nową planszę lub
  oryginalną planszę, jeśli nie można jej umieścić"
  [board piece place]
  (let [board-max (count (flatten board))
        zero-indexed-place (dec place)
        position (integer-to-coords board zero-indexed-place)]
    (if (and (< zero-indexed-place board-max)
             (= nil (get-in board position)))
      (assoc-in board position piece)
      board)))

(defn determine-outcome
  "Mając planszę i graczy, zgłoś wygraną lub remis"
  [board players]
  (if-let [winner (seq (filter (partial any-winners? board) players))]
    (first winner)
    (if (full? board)
      :draw
      nil)))
