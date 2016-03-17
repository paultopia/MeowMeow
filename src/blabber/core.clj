(ns blabber.core 
  (:require 
    [clojure.string :refer [lower-case split]]
    [clojure.walk :refer [keywordize-keys]]
    [clojure.core.matrix.dataset :refer [dataset]]))

(defn depunctuate 
  "strip punctuation from string"
  [string] 
  (apply str (filter #(or (Character/isLetter %) (Character/isSpace %)) string)))
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
  (map frequencies stringvecs))   
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

(defn CMat-TDM 
  "make a core.matrix dataset from vector of preprocessed docs"
  [preprocessed-docs]
  (dataset (unsorted-TD-map preprocessed-docs)))

; I don't need any of the rest of the stuff below now, core.matrix just handles the business.

(defn TD-map
  "make a sorted document-term-map of vector of preprocessed docs with keywords"
  [preprocessed-docs]
  (keywordize-keys  ; this is mainly for later flexibility
    (map #(into (sorted-map) %) (unsorted-TD-map preprocessed-docs))))
(defn TD-seqs
  "convert document-term-map into sequence of sequences"
  [tdmap]
  (cons 
    (keys (first tdmap))
    (map vals tdmap)))
(defn seqs-to-vecs 
  "sequence of sequences --> vector of vectors"
  [seqofseq]
  (into [] (map #(into [] %) seqofseq)))
(defn preprocessed-TD-matrix 
  "make term document matrix as vector of vectors from vector of preprocessed docs"
  [preprocessed-docs]
  (-> preprocessed-docs TD-map TD-seqs seqs-to-vecs))
(defn make-TD-matrix
  "preprocess docs then make term document matrix out of them"
  ([docs] 
   (preprocessed-TD-matrix docs))
  ([docs & funcs]
   (let [preproc (apply comp funcs)]
     (preprocessed-TD-matrix (map preproc docs)))))



