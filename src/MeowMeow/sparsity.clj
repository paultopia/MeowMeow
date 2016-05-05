(ns MeowMeow.sparsity
  (:require [MeowMeow.utilities :refer :all]))

;; sparsity: 
;; take decimal from user 
;; filter columns by frequencies list/numdocs > that decimal. 

(defn- rowfilter
  [row level tester]
  (filter-by-other row tester #(>= % level)))

(defn filter-sparsity 
  "filter tdm by level, where level is a decimal representing the proportion 
  of documents in which a token must appear in order to be included"
  [tdm level]
  (let [len (count tdm) tester (mapv #(/ % len) (count-column-presences tdm))]
    (mapv #(rowfilter % level tester) tdm)))