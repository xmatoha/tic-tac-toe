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

(def app
  (http/ring-handler
   (http/router
    [["/swagger.json"
      {:get {:no-doc true
             :swagger {:info {:title "tic-tac-toe api"
                              :description "tic-tac-toe api"}}
             :handler (swagger/create-swagger-handler)}}]
     ["/health" {:get {:summary "service health check"
                       :responses {200 {:body {:msg string?}}}
                       :handler health-handler}}]]

    {:exception pretty/exception
     :data {:coercion reitit.coercion.spec/coercion
            :muuntaja m/instance
            :interceptors [;; swagger feature
                           swagger/swagger-feature
                             ;; query-params & form-params
                           (parameters/parameters-interceptor)
                             ;; content-negotiation
                           (muuntaja/format-negotiate-interceptor)
                             ;; encoding response body
                           (muuntaja/format-response-interceptor)
                             ;; exception handling
                           (exception/exception-interceptor)
                             ;; decoding request body
                           (muuntaja/format-request-interceptor)
                             ;; coercing response bodys
                           (coercion/coerce-response-interceptor)
                             ;; coercing request parameters
                           (coercion/coerce-request-interceptor)]}})

   (ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :operationsSorter "alpha"}})
    (ring/create-default-handler))
   {:executor sieppari/executor}))

(defn get-port []
  (if-let [port  (System/getenv "PORT")] (Integer/valueOf port) 3000))

(defn start []
  (jetty/run-jetty #'app {:port (get-port), :join? false, :async true})
  ;(aleph/start-server (aleph/wrap-ring-async-handler #'app) {:port 3000})
  (println (str "Server running in port" (get-port))))

(comment
  (start))


