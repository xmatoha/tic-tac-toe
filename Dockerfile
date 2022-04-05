from clojure
COPY . .
RUN lein test
RUN lein uberjar
