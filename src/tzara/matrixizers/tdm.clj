(ns tzara.matrixizers.tdm
  (:require [tzara.matrixizers.utilities :refer [make-token-list concatenate-header-and-data]]))

(defn make-row
  "seq of all tokens + vec of tokens in document -> row in tdm"
  [all-tokens this-document]
  (let [freqs (frequencies this-document)]
    (reduce #(conj %1 (get freqs %2 0)) [] all-tokens)))

(defn make-tdm
  "seq of tokenized docs (i.e., seq of seqs of tokens) -> vec of vecs tdm WITHOUT LABELS (but preserves ordering so labels can be added back in later.)"
  [docs]
  (let [all-tokens (make-token-list docs)
        rows (mapv (partial make-row all-tokens) docs)]
    (concatenate-header-and-data all-tokens rows)))
