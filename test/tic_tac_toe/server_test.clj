(ns tic-tac-toe.server-test
  (:require [clojure.test :refer [deftest is testing]]
            [tic-tac-toe.server :refer [get-port]]))

(deftest get-port-test
  (testing "it should take port value from env if defined otherwise take default value"
    (is (= 3000 (get-port)))))
