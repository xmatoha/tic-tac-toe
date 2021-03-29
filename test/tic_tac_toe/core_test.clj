(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))

(deftest board-test
  (testing "describe board"
    (testing "empty board of size 3x3 should contain 9 slots"
      (is (= 9 (->> (empty-board 3)
                    (count)))))
    (testing "all board cells should contain offset property"
      (is (= 9
             (->> (empty-board 3)
                  (filter (fn [e] (:offset e)))
                  (count)))))
    (testing "all board cells on newly created board should be empty"
      (is (= 9
             (->> (empty-board 3)
                  (filter (fn [e] (= :e (:state e))))
                  (count)))))))

(deftest board-operations
  (testing "define board operations"
    (testing "occupy row 0 and column 0 by player X"
      (is (= :x
             (->
              (empty-board 3)
              (occupy 0 0 :x)
              (subvec 0 1)
              (first)
              (:state)))))
    (testing "should return row identified by index"
      (is (= [{:offset 0 :state :x} {:offset 1 :state :e} {:offset 2 :state :e}]
             (->
              (empty-board 3)
              (occupy 0 0 :x)
              (row 0)))))
    (testing "should return column by col index"
      (is (= [{:offset 1 :state :e} {:offset 4 :state :x} {:offset 7 :state :e}]
             (->
              (empty-board 3)
              (occupy 1 1 :x)
              (col 1)))))
    (testing "should return ascending diagnolae"
      (is (= [{:offset 0 :state :e} {:offset 4 :state :x} {:offset 8 :state :e}]
             (->
              (empty-board 3)
              (occupy 1 1 :x)
              (asc-diagonale)))))
    (testing "should return descending diagnolae"
      (is (= [{:offset 2 :state :e} {:offset 4 :state :x} {:offset 6 :state :e}]
             (->
              (empty-board 3)
              (occupy 1 1 :x)
              (desc-diagonale)))))))

(deftest winning-scenarios
  (testing "describe winning scenarios"
    (testing "player X won if all cells horizontally are occupied by player X"
      (is (= true (->
                   (empty-board 3)
                   (occupy 0 0 :x)
                   (occupy 0 1 :x)
                   (occupy 0 2 :x)
                   (won? :x)))))

    (testing "player X won if all vertical cells are occupied by player X"
      (is (= true (->
                   (empty-board 3)
                   (occupy 0 0 :x)
                   (occupy 1 0 :x)
                   (occupy 2 0 :x)
                   (won? :x)))))
    (testing "player X won if asc diagonale is all X"
      (is (= true (->
                   (empty-board 3)
                   (occupy 0 0 :x)
                   (occupy 1 1 :x)
                   (occupy 2 2 :x)
                   (won? :x)))))
    (testing "player X won if desc diagonale is all X"
      (is (= true (->
                   (empty-board 3)
                   (occupy 0 2 :x)
                   (occupy 1 1 :x)
                   (occupy 2 0 :x)
                   (won? :x)))))))

(deftest player-making-move
  (testing "describe how player makes move"
    (testing "player move is picket random from empty cells"
      (is (= 1
             (->
              (filter (fn [e] (= (:state e) :x))
                      (->
                       (empty-board 3)
                       (make-move :x)))
              (count)))))
    (testing "player X should pick last empty cell if other cells are taken"
      (is (= 1

             (->
              (filter (fn [e] (= (:state e) :x))
                      (->
                       (vec (map (fn [e] (assoc e :state :o)) (empty-board 3)))
                       (occupy 0 0 :e)
                       (make-move :x)))
              (count)))))))

(deftest display-board-test
  (testing "describe how board is displayed on screen"
    (testing "row X,empty,O should render as follows 'X| |O' "
      (is (= "X| |O" (row-to-string [{:state :x} {:state :e} {:state :o}]))))
    (testing "row separator should be -+-+-"
      (is (= "-+-+-" (row-separator 3))))
    (testing "should display empty board using row and row separator"
      (is (= " | | \n-+-+-\n | | \n-+-+-\n | | " (board-to-string (empty-board 3)))))))

