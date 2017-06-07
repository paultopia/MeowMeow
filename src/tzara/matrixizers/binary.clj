(ns tzara.matrixizers.binary
  "make a binary tdm, i.e., each row has 1 if term appears in document, regardless of number of appearances, and 0 otherwise.  Public function: make-binary-tdm"
  (:require [tzara.matrixizers.tdm :refer [make-tdm]]))

(defn binary-token-presence
  "replace tdm rows (seq of seq of tokens) with 1 if in dataset or 0 if not. (might pull out as separate representation actually)"
  [s]
  (mapv #(mapv (partial min 1) %) s))

(defn make-binary-tdm
  [docs]
  (let [[header & data] (make-tdm docs)
        binary-data (binary-token-presence data)]
    (reduce conj [header] binary-data)))
