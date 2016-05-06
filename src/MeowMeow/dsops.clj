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
    {:labels (last sps) :frequencies WHAT-IS-THE-REST-EXCEPT-LAST??}))
;;

;; this one is a little harder.  just concatenating it from bottom and .

;; also should I maybe make this work with the same labels core.matrix uses for them?
;; probably, so things can just be passed in and out easily.  need to check labels.
;; actually, screw it, I'll just provide a converter in core. 