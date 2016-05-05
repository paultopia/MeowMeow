;; this is the namespace for operations on an entire dataset, represented as a 
;; term document matrix.  Many implementations are delegated to other namespaces
;; such as MeowMeow.tfidf (tf-idf scoring), MeowMeow.sparsity (removal of sparse terms) 

(ns MeowMeow.dsops
  (:require [MeowMeow.tfidf :refer :all]
            [MeowMeow.sparsity :refer :all]))

;; this needs to take a map qua tdm and score the matrix part, then do ditto with sparsity