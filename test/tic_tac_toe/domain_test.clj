(ns tic-tac-toe.domain-test
  (:require [clojure.test :refer [deftest is testing]]
            [mockfn.macros :as mfn]
            [mockfn.matchers :as matchers]
            [mockfn.clj-test :as t]
            [tic-tac-toe.domain :refer [empty-board game-loop occupy won? game-round display-game]]))

(deftest game-loop-tests
  (testing "game should finish "
    (is (= true (:game-over (last  (game-loop :x))))))
  (testing "game should have a winner declared"
    (is (some? (:winner (last  (game-loop :x)))))))

(deftest game-state-tests
  (testing "given row winnig board it should evaluate it properly"
    (is (= true (-> (empty-board 3)
                    (occupy 0 0 :x)
                    (occupy 1 0 :x)
                    (occupy 2 0 :x)
                    (won? :x)))))
  (testing "given column winnig board it should evaluate it properly"
    (is (= true (-> (empty-board 3)
                    (occupy 0 0 :x)
                    (occupy 0 1 :x)
                    (occupy 0 2 :x)
                    (won? :x)))))
  (testing "given asc diagnoale winnig board it should evaluate it properly"
    (is (= true (-> (empty-board 3)
                    (occupy 0 0 :x)
                    (occupy 1 1 :x)
                    (occupy 2 2 :x)
                    (won? :x)))))
  (testing "given desc diagnoale winnig board it should evaluate it properly"
    (is (= true (-> (empty-board 3)
                    (occupy 0 2 :x)
                    (occupy 1 1 :x)
                    (occupy 2 0 :x)
                    (won? :x)))))
  (testing "given not winning board won should be false"
    (is (= false (-> (empty-board 3)
                     (occupy 0 2 :x)
                     (occupy 1 2 :e)
                     (occupy 2 0 :x)
                     (won? :x))))))

(deftest game-round-tests
  (testing "given game-state after making round next player should switch",
    (is (= :o (:next-player (game-round {:current-board (empty-board 3) :next-player :x}))))))

(t/deftest game-display-tests
  (t/testing "given certain game history it should properly render it on screen"
    (mfn/verifying
     [(println "**************************************") nil (matchers/exactly 2)
      (println "new game ") nil (matchers/exactly 1)
      (println (matchers/pred string?)) nil (matchers/exactly 3)]
     (is (= nil (display-game [(game-round {:current-board (empty-board 3) :next-player :x})]))))))

