(ns tic-tac-toe.api)

(defn health-handler [request]
  {:status 200
   :body {:msg "ok"}})
