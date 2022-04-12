(defproject tic-tac-toe :project/status-to-version
  :description "tic tac toe kata"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-kondo "2022.03.09"]
                 [org.clojure/clojure "1.10.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [aleph "0.4.7-alpha5"]
                 [metosin/reitit "0.5.17"]
                 [nubank/mockfn "0.7.0"]]
  :main ^:skip-aot tic-tac-toe.core
  :target-path "target/%s"
  :aliases {"test" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "unit"]
            "features" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "features"]
            "watch" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner" "--fail-fast" "--watch" "unit"]
            "lint" ["run" "-m" "clj-kondo.main" "--lint" "src"]}
  :plugins [[refactor-nrepl "3.5.2"]
            [cider/cider-nrepl "0.28.3"]
            [me.arrdem/lein-git-version "2.0.3"]]

  :git-version
  {:status-to-version
   (fn [{:keys [tag version branch ahead ahead? dirty?] :as git}]
     (assert (re-find #"\d+\.\d+\.\d+" tag)
             "Tag is assumed to be a raw SemVer version")
     (if (and tag (not ahead?) (not dirty?))
       tag
       (let [[_ prefix patch] (re-find #"(\d+\.\d+)\.(\d+)" tag)
             patch            (Long/parseLong patch)
             patch+           (inc patch)]
         (format "%s.%d" prefix patch+))))}

  :profiles {:kaocha {:dependencies [[lambdaisland/kaocha-cucumber "0.0-53"]
                                     [lambdaisland/kaocha "1.64.1010"]
                                     [lambdaisland/kaocha-cloverage "1.0.75"]]}
             :uberjar {:aot :all}})


