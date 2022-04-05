(ns tic-tac-toe.domain-test
  (:require [clojure.test :refer [deftest is testing]]
            [tic-tac-toe.core :refer (empty-board)]))

(deftest empty-board-test
  (testing "board should have board-size * board-size free slots"
    (let [board-size 3]
      (is (= (* board-size board-size) (count (empty-board board-size)))))))
