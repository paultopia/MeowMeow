(defproject tzara "0.1.0"
  :description "A text-mining utility library."
  :url "https://github.com/paultopia/tzara"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"] 
                 [clj-fuzzy "0.4.0"]
		 [org.clojure/tools.cli "0.3.5"]
		 [org.clojure/data.csv "0.1.4"]
 		 [ring/ring-core "1.6.1"]
		 [ring/ring-jetty-adapter "1.6.1"]
		 [ring/ring-json "0.4.0"]
                 [snowball-stemmer "0.1.0"]
                 [net.mikera/core.matrix "0.60.3"]
		 [org.clojure/data.json "0.2.6"]]
:main tzara.cli.core)
