(ns MeowMeow.featurizer
  "Core abstraction: the featurizer. Idea is that you should be able to pass 
  a vector of texts, and any function that produces a vector of features from 
  text, to a higher-order function and that higher-order function should 
  extract the features, count them, and return a universal map with counts across 
  the entire dataset (vector of texts).  Also includes utility functionality for 
  taking preexisting maps of text + feature and combining them, taking regexes 
  and applying as featurizer functions, etc.")


(defn- count-features
  "count frequencies of features in vector of vectors of features"
  [featurevecs]
  (mapv frequencies featurevecs))

(defn- list-features
  "list all features in doc set"
  [token-vecs]
  (distinct
    (apply concat token-vecs)))

(defn- cartesian-map
  [featurelist]
  (zipmap featurelist (repeat 0)))

(defn- sparsify-counts
  "based on features in all preprocesed docs, fill counts with 0 for 
  unused features in each single preprocessed doc"
  [zeroes counts]
  (mapv #(merge-with + % zeroes) counts))

;; public functions follow

(defn featurizer
  "vector of documents + feature extracter function --> vector of maps with 
  feature counts. Single-arity version is for convenience in tdm namespace"
  ([vec-of-features feature-fn]
    (let [feature-vecs (mapv feature-fn vec-of-features)]
      (featurizer feature-vecs)))
    ([vec-of-features]
      (sparsify-counts
        (-> vec-of-features list-features cartesian-map)
        (-> vec-of-features count-features))))

; TODO: fulfil the promise in the ns docfeature: "Also includes utility functionality for 
;  taking preexisting maps of text + feature and combining them, taking regexes 
;  and applying as featurizer functions, etc."

; also make it return original text and change tests accordingly?  or is that an 
; unnecessarily big refactor?  Instead, can I write a workflow to just combine feature 
; extraction tasks or add extracted features to an existing map?
;
; that latter is the way to go I think: in this NS, write something to add 
; features to an existing td-map or td-matrix, that also checks existing 
; keys/columns (recursively), renames to avoid conflicts, and returns new one.
 

