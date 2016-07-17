(ns tzara.tdm
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
This should all just pass seamlessly to any core.matrix flavor."
 (:require [tzara.featurizer :as fea]))


(defn td-maps
  "vector of tokenized documents --> vector of maps with counts, including zeroes for terms 
  that do not appear in a given document.  Note that this is suitable for passing directly 
  into a core.mayrix dataset if that suits your fancy."
  [token-vecs]
    (fea/featurizer token-vecs))

(defn td-matrix
  "vector of matrices --> TDM proper as map w/ :labels :data.  N.B. to pass 
  it to something that implements the core.matrix dataset protocol, just 
  send the labels as a vector and the frequencies as nested vectors, like 
  (clojure.core.matrix.dataset/dataset (:labels your-tdm) (:frequencies your-tdm))"
  [tdmaps]
  (let [smaps (mapv #(into (sorted-map) %) tdmaps)]
    {:labels (vec (keys (first smaps))) 
     :frequencies (mapv vals smaps)}))

