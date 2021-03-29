(ns tic-tac-toe.core)

(defn empty-board [board-size]
  (->>
   (range (* board-size board-size))
   (map (fn [idx] {:offset idx :state :e}))
   (into [])))

(defn occupy [board row column who]
  (let [offset (+ (* 3 (- row 1)  (- column 1)))
        elem (first (subvec board offset 1))]
    (->>
     (assoc elem :state :x)
     (assoc board offset))))

