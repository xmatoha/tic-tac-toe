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
    (testing "occupy row 1 and column 1 by player X"
      (is (= :x
             (->
              (empty-board 3)
              (occupy 1 1 :x)
              (subvec 0 1)
              (first)
              (:stateq)))))))

(deftest winning-scenarios
  (testing "describe winning scenarios"
    (testing "player X won if all cells horizontally are occupied by player X"
      ())))


