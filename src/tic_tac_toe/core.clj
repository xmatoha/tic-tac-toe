(ns tic-tac-toe.core)

(defn empty-board [board-size]
  (->>
   (range (* board-size board-size))
   (map (fn [idx] {:offset idx :state :e}))
   (into [])))

(defn calc-board-size [board]
  (int (Math/sqrt (count board))))

(defn calc-offset [row column board]
  (+ (* (calc-board-size board) row)   column))

(defn occupy [board row column who]
  (let [offset (calc-offset row column board)
        elem (first (subvec board offset (+ offset 1)))]
    (->>
     (assoc elem :state :x)
     (assoc board offset))))

(defn row [board row-offset])

(defn won? [board who]
  (-> (map (fn [e] (if (= e 3) true false))
           (for [row (range 0 (calc-board-size board))]
             (->>
              (subvec board (calc-offset row 0 board))
              (filter (fn [e] (= :x (:state e))))
              (count))))
      (some?)))


