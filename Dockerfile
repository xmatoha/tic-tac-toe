from clojure
COPY project.clj .
RUN lein deps
COPY . .
RUN lein test
RUN lein uberjar
