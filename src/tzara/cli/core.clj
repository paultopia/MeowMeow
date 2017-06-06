(ns tzara.cli.core
  (:require [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-s" "--server PORT" "Start an HTTP server on specified port---can also take infile to initialize working data." :default false]
   ["-i" "--infile INFILE" "File (CSV or JSON) containing data to transform"]
   ["-c" "--config CONFIGFILE" "JSON configuration file describing transforms (CLI usage only)" :default false]
   ["-o" "--outfile OUTFILE" "Filename for CSV output (CLI usage only)" :default false]
   ["-h" "--help" "Print this CLI help" :default false]
   ["-w" "--webhelp" "Print instructions for interaction with http server" :default false]])

(def webhelp "This is web help. Stub stub stub.")

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
    (cond
      (:webhelp options) (println webhelp)
      (:help options) (println summary)
      :else (println "bad command")
      )
    ))

;; server will appear in server, or be false, etc.  All under options.
