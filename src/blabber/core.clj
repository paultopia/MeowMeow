(ns blabber.core
  (:require
    [clojure.string :refer [lower-case split]]
    [clojure.walk :refer [keywordize-keys]]
    [clojure.core.matrix.dataset :refer [dataset merge-datasets]]
    [clojure.data.json :as json]))


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

(defn extract-texts
  "map of docs and labels, like from json --> extract the docs. all labels assumed strings"
  [docmap text-label]
  {:texts (map #(get % text-label) docmap) :features (map #(dissoc % text-label) docmap)})

(defn map-with-json [])

(defn doc-feature-matrix
  "make combined matrix out of labelled data"
  ([docmap text-label]
   (merge-datasets (make-TD-matrix (:texts (extract-texts datarecs "text")))
                  (dataset (:features (extract-texts datarecs "text")))))
  ([docmap text-label & funcs]
   (merge-datasets (apply make-TD-matrix (:texts (extract-texts datarecs "text")) funcs)
                  (dataset (:features (extract-texts datarecs "text"))))))

; this is just some test code for json functionality. will go away soon.
(def datarecs (json/read-str (slurp "test.json")))
(doc-feature-matrix datarecs "text")
(doc-feature-matrix datarecs "text" denumber depunctuate tolower)

; ok, this needs some major cleanup but now I have the capacity to read a labelled json with text
; and get a labelled tdm out of it.

; probably the next step is to actually setup for tests.  That or do renaming and cleanup
