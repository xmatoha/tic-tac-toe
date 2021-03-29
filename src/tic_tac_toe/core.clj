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

(defn row [board row-offset]
  (subvec board (calc-offset row-offset 0 board) (+ (calc-offset row-offset 0 board) (calc-board-size board))))

(defn col [board col-offset])

(defn row-won? [board who]
  (->>
   (for [row-offset (range 0 (calc-board-size board))]
     (->>
      (row board row-offset)
      (every? (fn [e] (= (:state e) who)))))
   (some true?)))

(defn col-won? [board who])

(defn won? [board who]
  (cond (row-won? board who) true
        (col-won? board who) true
        :else false))

