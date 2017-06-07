(ns tzara.matrixizers.binary
  "make a binary tdm, i.e., each row has 1 if term appears in document, regardless of number of appearances, and 0 otherwise.  Public function: make-binary-tdm"
  (:require [tzara.matrixizers.tdm :refer [make-tdm]]
            [tzara.matrixizers.utilities :refer [concatenate-header-and-data]]))

(defn binary-token-presence
  "replace tdm rows (seq of seq of tokens) with 1 if in dataset or 0 if not. (might pull out as separate representation actually)"
  [s]
  (mapv #(mapv (partial min 1) %) s))

(defn make-binary-tdm
  [docs]
  (let [[header & data] (make-tdm docs)
        binary-data (binary-token-presence data)]
    (concatenate-header-and-data header binary-data)))
