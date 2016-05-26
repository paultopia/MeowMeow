(ns MeowMeow.tdm
  "functions to create a term-document matrix out of a collection of 
tokenized strings (seqable of seqable) (vectors, sequences, whev.).  
There are two public functions.  The first, td-maps, takes a straight seqable of seqable, 
and produces a vector of maps where each map represents a document and is 
{:token1 count token2: count...} for every token that appears in the entire dataset
The second, td-matrix takes a td-map and converts it to a single map: 
{:labels [vector of token labels] :frequencies [vec of seqs]} where the 
frequency vector-of-seqs is in standard term-document-matrix format (i.e., 
documents on the rows, counts on the columns) and labels are keywords
So obviously, the thing to do ordinarily is (td-matrix (td-map your-tokenized-dataset))
This should all just pass seamlessly to any core.matrix flavor.")

;; private functions

(defn- count-strings
  "count frequencies of strings in vector of vectors of strings"
  [stringvecs]
  (mapv frequencies stringvecs))

(defn- list-strings
  "list all strings in doc set"
  [token-vecs]
  (distinct
    (apply concat token-vecs)))

(defn- cartesian-map
  [stringlist]
  (zipmap stringlist (repeat 0)))

(defn- sparsify-counts
  "based on strings in all preprocesed docs, fill counts with 0 for unused strings in each single preprocessed doc"
  [zeroes counts]
  (mapv #(merge-with + % zeroes) counts))

;; public functions follow

(defn td-maps
  "vector of tokenized documents --> vector of maps with counts, including zeroes for terms 
  that do not appear in a given document.  Note that this is suitable for passing directly 
  into a core.mayrix dataset if that suits your fancy."
  [token-vecs]
    (sparsify-counts
      (-> token-vecs list-strings cartesian-map)
      (-> token-vecs count-strings)))

(defn td-matrix
  "vector of matrices --> TDM proper as map w/ :labels :data.  N.B. to pass 
  it to something that implements the core.matrix dataset protocol, just 
  send the labels as a vector and the frequencies as nested vectors, like 
  (clojure.core.matrix.dataset/dataset (:labels your-tdm) (:frequencies your-tdm))"
  [tdmaps]
  (let [smaps (mapv #(into (sorted-map) %) tdmaps)]
    {:labels (vec (keys (first smaps))) 
     :frequencies (mapv vals smaps)}))

;; this needs a refactor.  New concept/abstraction: featurizer---function that 
; gets features from documents.  Pass it to any seqable of documents and get back 
; a map or matrix.  Not just tokenizers but also regex extraction and other parsers.
