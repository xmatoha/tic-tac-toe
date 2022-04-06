(ns tic-tac-toe.server-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [muuntaja.core :as m]
   [reitit.coercion.spec]
   [reitit.exception :as pretty]
   [reitit.http.coercion :as coercion]
   [reitit.http.interceptors.exception :as exception]
   [reitit.http.interceptors.muuntaja :as muuntaja]
   [reitit.http.interceptors.parameters :as parameters]
   [reitit.swagger :as swagger]
   [tic-tac-toe.server :refer [get-port merge-env router-config]]))

(deftest get-port-test
  (testing "it should take default port value"
    (is (= 3000 (get-port (merge-env {})))))
  (testing "it should take port value from env"
    (is (= 5000 (get-port (merge-env {"PORT" "5000"}))))))

