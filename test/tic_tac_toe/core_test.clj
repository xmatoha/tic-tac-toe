(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))

(deftest board-test
  (testing "describe board"
    (testing "empty board of size 3x3 should contain 9 slots"

      (is (= 9 (->> (empty-board 3)
                    (count)))))))
