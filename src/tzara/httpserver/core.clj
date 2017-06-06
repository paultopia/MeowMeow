(ns tzara.httpserver.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body]]
            [clojure.data.csv :refer [write-csv]]))

;; this function is for testing, takes json of form {:foo 1 :bar 2}.  when this is finished it will instead be a function that takes the appropriate bits of a combined data/commands json and returns a correct csv with all processed.
(defn send-stub-csv [m]
  (let [k (mapv name (keys m))
        {:keys [foo bar]} m]
    [k [(:foo m) (:bar m)]]))

;; nested vectors to csv string
(defn csv-string [v]
  (with-out-str (write-csv *out* v)))


(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/csv"}
   :body (csv-string (send-stub-csv (:body request)))})

(def receiver (wrap-json-body handler {:keywords? true :bigdecimals? true}))

(defn run-webserver! [port]
  (run-jetty receiver {:port port}))

;; manually tested and works.  right now, just mirrors content back.  to test:
;;
;; lein repl
;;
;; (use 'tzara.httpserver.core)
;; (run-webserver! 3000)
;;
;; curl -H "Content-Type: application/json" -X POST -d '{"foo":"1","bar":"2"}' http://localhost:3000/
;;
;; also works without quotes on numbers
