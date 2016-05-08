(ns MeowMeow.dsops
  "This is the namespace for operations on an entire dataset, represented as a 
  term document matrix.  Many implementations are delegated to other namespaces
  such as MeowMeow.tfidf (tf-idf scoring), MeowMeow.sparsity (removal of sparse terms). 
  Note that the tfidf and sparsity namespaces work on values alone, represented as 
  matrix (nested vectors/sequences). They do not work on datasets represented as 
  a map of feature labels and matrix.  For that, use this namespace."
    (:require [MeowMeow.tfidf]
            [MeowMeow.sparsity]))

(defn tfidf
  "Replace token frequencies with tf-idf scores.  For further documentation see 
  the function of the same name in the tfidf namespace." 
  ([dataset]
    {:labels (:labels dataset) :frequencies (MeowMeow.tfidf/tfidf (:frequencies dataset))})
  ([dataset flag]
    {:labels (:labels dataset) :frequencies (MeowMeow.tfidf/tfidf (:frequencies dataset flag))}))

(defn sparsity 
  "Remove tokens that appear in less than level 
  (as decimal) proportion of documents."
  [dataset level]
  (let [bigds (conj (:frequencies dataset) (:labels dataset)) sps (sparsity bigds level)]
    {:labels (last sps) :frequencies (vec (doall (drop-last sps)))}))

(defn- merge-mat [firstd secondd] (mapv (comp vec flatten vector) firstd secondd))

(defn merge-data
  "Merge a TDM, in {:labels :frequencies} format, with any other similar 
  dataset---for example, to combine with other labels for your set of documents"
  [termdataset featuredataset]
  {:labels ((comp vec flatten vector) (:labels termdataset) (:labels featuredataset))
   :frequencies (merge-mat (:frequencies termdataset) (:frequencies featuredataset))})

