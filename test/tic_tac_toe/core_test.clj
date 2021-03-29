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
              (col 1)))))))

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
                   (won? :x)))))))



