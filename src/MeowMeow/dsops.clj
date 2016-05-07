;; this is the namespace for operations on an entire dataset, represented as a 
;; term document matrix.  Many implementations are delegated to other namespaces
;; such as MeowMeow.tfidf (tf-idf scoring), MeowMeow.sparsity (removal of sparse terms) 

;; note that the tfidf and sparsity namespaces work on values alone, represented as 
;; matrix (nested vectors/sequences). They do not work on datasets represented as 
;; a map of feature labels and matrix.  For that, use this namespace.


(ns MeowMeow.dsops
  (:require [MeowMeow.tfidf]
            [MeowMeow.sparsity]))

(defn tfidf 
  ([dataset]
    {:labels (:labels dataset) :frequencies (MeowMeow.tfidf/tfidf (:frequencies dataset))})
  ([dataset flag]
    {:labels (:labels dataset) :frequencies (MeowMeow.tfidf/tfidf (:frequencies dataset flag))}))

(defn sparsity 
  [dataset level]
  (let [bigds (conj (:frequencies dataset) (:labels dataset)) sps (sparsity bigds level)]
    {:labels (last sps) :frequencies (vec (doall (drop-last sps)))}))

(defn- merge-mat [firstd secondd] (mapv (comp vec flatten vector) firstd secondd))

(defn merge-data
  "suppose you've got a term-document matrix here, plus a separate non-text-mined 
  feature dataset from the same set of documents. It would be nice to combine them. 
  Send them to this fxn (tdm first) in the same format as tdm datasets here 
  (as a map with a :labels vector and a :frequencies vector of vectors)"
  [termdataset featuredataset]
  {:labels ((comp vec flatten vector) (:labels termdataset) (:labels featuredataset))
   :frequencies (merge-mat (:frequencies termdataset) (:frequencies featuredataset))})


;;

;; this one is a little harder.  just concatenating it from bottom and .

;; also should I maybe make this work with the same labels core.matrix uses for them?
;; probably, so things can just be passed in and out easily.  need to check labels.
;; actually, screw it, I'll just provide a converter in core. 