(ns tic-tac-toe.core
  (:require [tic-tac-toe.server :refer [start]])
  (:gen-class :main true))

(defn -main
  [& args]
  (start))
