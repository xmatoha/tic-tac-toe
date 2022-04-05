(ns tic-tac-toe.api-test
  (:require [clojure.test :refer [deftest is testing]]
            [tic-tac-toe.api :refer [health-handler]]))

(deftest health-check-test
  (is (= {:status 200 :body {:msg "ok"}} (health-handler [{}]))))
