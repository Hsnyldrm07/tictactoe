(ns ttt.core

  ;; GRA: TIC-TAC-TOE
  ;; Autor: Hasan YILDIRIM nr.albumu 36439

  (:require [ttt.game :as game]
            [ttt.view :as view]))

(def size 3)            ;; ustawić rozmiar planszy
(def players ["X" "O"]) ;; lLista graczy
(def turns (cycle players))

(defn board []
  "Zwróć planszę ttt.core / size"
  (vec (repeat size (vec (repeat size nil)))))

(defn -main
  []
  (view/print-introduction players)
  (let [turns (cycle players)]
    (loop
        [board (board)
         turns turns]
      (if-let [winner-or-draw (game/determine-outcome board players)]
        (do
          (view/print-board board)
          (if (= :draw winner-or-draw)
            (println "Rysunek!")
            (println winner-or-draw "wygrywa"))
          (println "Do widzenia"))
        (let [player (first turns)]
          (do (view/print-board board)
              (println "Gracz" player "> ")
              (let [next-move (read-string (read-line))
                    new-board (game/place-piece board player next-move)]
                (if (not= new-board board)
                  (recur new-board (rest turns))
                  (do (println "Niedozwolony ruch")
                      (recur board turns))))))))))
