(ns blabber.core
  (:require
    [clojure.string :refer [lower-case split]]
    [clojure.core.matrix.dataset :refer [dataset merge-datasets]]
    [blabber.document :refer :all]
    [blabber.tokens :refer :all]
    [blabber.tdm :refer :all]
    [blabber.tfidf :refer :all]))




;; get rid of me: not relying on core.matrix anymore.
(defn preprocessed-TD-matrix
  "make a core.matrix dataset from vector of preprocessed docs"
  [preprocessed-docs]
  (dataset (unsorted-TD-map preprocessed-docs)))

;; get rid of me: not relying on core.matrix anymore, and not passing functions in this way.
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

;; this should now just call to the implementation in the tdm namespace. plus something in 
;; some other namespace to handle feature labels. (Tdm namespace just makes a tdm w/o labels)
(defn doc-feature-matrix
  "make combined matrix out of labelled data"
  ([docmap text-label]
   (merge-datasets (make-TD-matrix (:texts (extract-texts docmap text-label)))
                  (dataset (:features (extract-texts docmap text-label)))))
  ([docmap text-label & funcs]
   (merge-datasets (apply make-TD-matrix (:texts (extract-texts docmap text-label)) funcs)
                  (dataset (:features (extract-texts docmap text-label))))))



; this is just some test code for json functionality. will go away soon.
; (require 'clojure.data.json)
; (def datarecs (clojure.data.json/read-str (slurp "test.json")))
; (doc-feature-matrix datarecs "text")
; (doc-feature-matrix datarecs "text" denumber depunctuate tolower)

; ok, this needs some major cleanup but now I have the capacity to read a labelled json with text
; and get a labelled tdm out of it.

; probably the next step is to actually setup for tests.  That or do renaming and cleanup



; starting some ngram functionality
; assumption: I have a vector of tokens in original order and I want to extract ngrams of arbitrary size from it.





