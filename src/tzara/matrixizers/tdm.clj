(ns tzara.matrixizers.tdm
  (:require [tzara.utilities :refer [csv-string]]
            [tzara.matrixizers.utilities :refer [make-token-list]]))

(defn make-row
  "seq of all tokens + vec of tokens in document -> row in tdm"
  [all-tokens this-document]
  (let [freqs (frequencies this-document)]
    (reduce #(conj %1 (get freqs %2 0)) [] all-tokens)))

(defn make-tdm
  "seq of tokenized docs (i.e., seq of seqs of tokens) -> csv string tdm WITHOUT LABELS (but preserves ordering so labels can be added back in later.)"
  [docs]
  (let [all-tokens (make-token-list docs)
        rows (mapv (partial make-row all-tokens) docs)
        full-matrix (reduce conj [all-tokens] rows)] ; this is a perf crime, prepending to vector this way.  need to fix.
    (csv-string full-matrix)))
