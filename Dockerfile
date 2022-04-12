from clojure as build
RUN mkdir /opt/app
WORKDIR /opt/app

COPY project.clj .
RUN lein deps
COPY . .
# RUN lein lint
# RUN lein test
RUN lein uberjar
RUN chgrp root /opt/app && chmod ug+rwX /opt/app && chmod a+rX /opt/app
CMD ["java", "-jar", "target/uberjar/tic-tac-toe-0.1.0-SNAPSHOT-standalone.jar"]
