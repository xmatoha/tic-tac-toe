from clojure as build
COPY project.clj .
RUN lein deps
COPY . .
RUN lein test
RUN lein uberjar
from clojure as run
COPY --from=build /tmp/target/uberjar/tic-tac-toe-0.1.0-SNAPSHOT-standalone.jar tic-tac-toe.jar
RUN chmod g+rwX /tmp/tic-tac-toe.jar
CMD ["java", "-cp", "/tmp/tic-tac-toe.jar", "tic_tac_toe.main"]
