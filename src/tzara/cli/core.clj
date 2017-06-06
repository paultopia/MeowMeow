(ns tzara.cli.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tzara.httpserver.core :refer [run-webserver!]]
            [clojure.data.json :refer [read-str]]))

(def cli-options
  [["-s" "--server PORT" "Start an HTTP server on specified port---can also take infile to initialize working data."]
   ["-i" "--infile INFILE" "File (CSV or JSON) containing data to transform"]
   ["-c" "--config CONFIGFILE" "JSON configuration file describing transforms (CLI usage only)"]
   ["-o" "--outfile OUTFILE" "Filename for CSV output (CLI usage only)"]
   ["-h" "--help" "Print this CLI help" :default false]
   ["-w" "--webhelp" "Print instructions for interaction with http server" :default false]])

(def webhelp "This is web help. Stub stub stub.")

(defn save-data [infile] (println "this is a stub function for saving incoming data"))
;; this will save data for website

(defn process-cli-data [infile config outfile]
  (println "this is a stub!  will actually, you know, process the data. \n\n" infile "\n\n" outfile "\n\n" config))
;; needs a default value for outfile too.


(defn dispatch [{:keys [server config infile outfile] :as opts}]
  (if server
    (do
      (run-webserver! (Integer/parseInt server))
      (if infile (save-data infile) (println "no incoming data")))
    (process-cli-data infile config outfile)))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (cond
      (:webhelp options) (println webhelp)
      (:help options) (println summary)
      (not-any? identity (vals options)) (println summary)
      :else (dispatch options)
      )
    ))

;; server will appear in server, or be false, etc.  All under options.
;; it would be nice to be able to handle streams for the cli somehow... and maybe even for webserver?

;; needs error handling.
