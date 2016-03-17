(ns blabber.core 
  (:require 
    [clojure.string :refer [lower-case split]]
    [clojure.walk :refer [keywordize-keys]]
    [clojure.core.matrix.dataset :refer [dataset]]))

(def tolower lower-case)

(defn depunctuate 
  "strip punctuation from string"
  [string] 
  (apply str (filter #(or (Character/isLetter %) (Character/isDigit %) (Character/isSpace %)) string)))

(def digit? #(Character/isDigit %))

(defn denumber 
  "strip numbers from string"
  [string] 
  (apply str (filter #(not (digit? %)) string)))

; should probably just use remove for that rather than going wild to invert filter

(defn default-preprocess 
  "sensible default preprocessing" 
  [string]
  (-> string depunctuate lower-case))
(defn whitespace-split 
  "split a vector of preprocessed strings into vector of vectors of strings on whitespace" 
  [preprocessed-docs] 
  (pmap #(split % #"\s") preprocessed-docs))
(defn count-strings 
  "count frequencies of strings in vector of vectors of strings" 
  [stringvecs]
  (pmap frequencies stringvecs))   
(defn list-strings 
  "list all strings in doc set" 
  [stringvecs]
  (distinct 
    (apply concat stringvecs)))
(defn cartesian-map 
  [stringlist] 
  (zipmap stringlist (repeat 0)))
(defn sparsify-counts 
  "based on strings in all preprocesed docs, fill counts with 0 for unused strings in each single preprocessed doc"
  [zeroes counts]
  (map #(merge-with + % zeroes) counts))
(defn unsorted-TD-map
  "split vector of preprocessed docs by spaces then make zero-filled map of counts"
  [preprocessed-docs]
  (let [stringvecs (whitespace-split preprocessed-docs)]
    (sparsify-counts 
      (-> stringvecs list-strings cartesian-map) 
      (-> stringvecs count-strings))))  

(defn preprocessed-TD-matrix  
  "make a core.matrix dataset from vector of preprocessed docs"
  [preprocessed-docs]
  (dataset (unsorted-TD-map preprocessed-docs)))

(defn make-TD-matrix
  "preprocess docs then make term document matrix out of them"
  ([docs] 
   (preprocessed-TD-matrix docs))
  ([docs & funcs]
   (let [preproc (apply comp funcs)]
     (preprocessed-TD-matrix (pmap preproc docs)))))



