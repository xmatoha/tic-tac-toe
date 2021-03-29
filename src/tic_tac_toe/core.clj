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
  (subvec board
          (calc-offset row-offset 0 board)
          (+ (calc-offset row-offset 0 board) (calc-board-size board))))

(defn col [board col-offset]
  (flatten (for [row-offset (range 0 (calc-board-size board))]
             (subvec board
                     (calc-offset row-offset col-offset board)
                     (+ 1 (calc-offset row-offset col-offset board))))))

(defn asc-diagonale [board]
  (flatten (for [row-offset (range 0 (calc-board-size board))]
             (subvec board
                     (calc-offset row-offset row-offset board)
                     (+ 1 (calc-offset row-offset row-offset board))))))

(defn desc-diagonale [board]
  (flatten (for [row-offset (range (- (calc-board-size board) 1) -1 -1)]
             (subvec board
                     (calc-offset
                      (- (calc-board-size board) row-offset 1) row-offset board)
                     (+ 1
                        (calc-offset
                         (- (calc-board-size board) row-offset 1) row-offset board))))))

(defn row-won? [board who]
  (->>
   (for [row-offset (range 0 (calc-board-size board))]
     (->>
      (row board row-offset)
      (every? (fn [e] (= (:state e) who)))))
   (some true?)))

(defn col-won? [board who]
  (->>
   (for [row-offset (range 0 (calc-board-size board))]
     (->>
      (col board row-offset)
      (every? (fn [e] (= (:state e) who)))))
   (some true?)))

(defn asc-diagnoale-won? [board who]
  (every? (fn [e] (= (:state e) who))
          (asc-diagonale board)))

(defn desc-diagnoale-won? [board who]
  (every? (fn [e] (= (:state e) who))
          (desc-diagonale board)))

(defn won? [board who]
  (cond (row-won? board who) true
        (col-won? board who) true
        (asc-diagnoale-won? board who) true
        (desc-diagnoale-won? board who) true
        :else false))

(defn make-move [board who]

  board)
