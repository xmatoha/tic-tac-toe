from clojure
COPY project.clj .
RUN lein deps
COPY . .
RUN lein test
RUN lein uberjar
CMD ["java", "-cp", "target/uberjar/tic-tac-toe-0.1.0-SNAPSHOT-standalone.jar", "tic_tac_toe.main"]
