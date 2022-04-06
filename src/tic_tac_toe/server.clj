(ns tic-tac-toe.server
  (:require [reitit.ring :as ring]
            [reitit.http :as http]
            [reitit.dev.pretty :as pretty]
            [tic-tac-toe.api :refer [health-handler]]
            [reitit.swagger :as swagger]
            [muuntaja.core :as m]
            [reitit.http.coercion :as coercion]
            [reitit.coercion.spec]
            [ring.adapter.jetty :as jetty]
            [reitit.http.interceptors.muuntaja :as muuntaja]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.interceptor.sieppari :as sieppari]
            [clojure.spec.alpha :as s]
            [reitit.http.interceptors.parameters :as parameters]
            [reitit.http.interceptors.exception :as exception]))

(defn routes []
  [["/swagger.json"
    {:get {:no-doc true
           :swagger {:info {:title "tic-tac-toe api"
                            :description "tic-tac-toe api"}}
           :handler (swagger/create-swagger-handler)}}]
   ["/health" {:get {:summary "service health check"
                     :responses {200 {:body {:msg string?}}}
                     :handler health-handler}}]])

(defn router-config []
  {:exception pretty/exception
   :data {:coercion reitit.coercion.spec/coercion
          :muuntaja m/instance
          :interceptors [swagger/swagger-feature
                         (parameters/parameters-interceptor)
                         (muuntaja/format-negotiate-interceptor)
                         (muuntaja/format-response-interceptor)
                         (exception/exception-interceptor)
                         (muuntaja/format-request-interceptor)
                         (coercion/coerce-response-interceptor)
                         (coercion/coerce-request-interceptor)]}})

(defn app []
  (http/ring-handler
   (http/router (routes) (router-config))
   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :operationsSorter "alpha"}}))
   {:executor sieppari/executor}))

(defn get-port [env]
  (if-let [port  (get env "PORT")] (Integer/valueOf port) 3000))

(defn merge-env [e]
  (merge e (into {} (System/getenv))))

(defonce jetty (atom  {}))

(defn start [env]
  (let [port (get-port env)]
    (reset! jetty (jetty/run-jetty #'app {:port port , :join? false, :async true}))

    (println (str "Server running in port " port))))

(comment
  (start (merge-env {})))

(comment
  (.stop @jetty))

;(aleph/start-server (aleph/wrap-ring-async-handler #'app) {:port 3000})
