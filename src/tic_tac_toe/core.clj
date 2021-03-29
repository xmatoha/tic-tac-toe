(ns tic-tac-toe.core)

(defn empty-board [board-size]
  (->>
   (range (* board-size board-size))
   (map (fn [idx] {:offset idx :state :e}))
   (into [])))

(defn occupy [board row column who]

  (subvec board (+ (* 3 (- row 1)  (- column 1))) 1))

(occupy (empty-board 3) 1 1 :x)
