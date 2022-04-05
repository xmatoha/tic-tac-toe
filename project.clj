(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "tic tac toe kata"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-kondo "2022.03.09"]
                 [org.clojure/clojure "1.10.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [aleph "0.4.7-alpha5"]
                 [metosin/reitit "0.5.17"]]
  :main ^:skip-aot tic-tac-toe.core
  :target-path "target/%s"
  :aliases {"test" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]
            "features" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "features"]
            "watch" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "--watch"]
            "lint" ["run" "-m" "clj-kondo.main" "--lint" "src"]}
  :profiles {:kaocha {:dependencies [[lambdaisland/kaocha-cucumber "0.0-53"]
                                     [lambdaisland/kaocha "1.64.1010"]
                                     [lambdaisland/kaocha-cloverage "1.0.75"]]}
             :uberjar {:aot :all}})

