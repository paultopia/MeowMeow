(ns tzara.matrixizers.tfidf
  "tfidf calculation based on http://www.tfidf.com"
  (:require [tzara.matrixizers.tdm :as tdm]))

(defn colsums
  "sum columns of multidimensional seq"
  [s]
  (apply (partial mapv +) s))

(defn binary-token-presence
  "replace tdm rows (seq of seq of tokens) with 1 if in dataset or 0 if not. (might pull out as separate representation actually)"
  [s]
  (mapv #(mapv (partial min 1) %) s))

(defn term-frequencies
  "calculate term frequencies in row (seq of term counts)"
  [row]
  (let [doc-length-repeated (repeat (apply + row))]
    (mapv / row doc-length-repeated)))

(defn inverse-doc-frequencies [numdocs appearances]
  (mapv #(Math/log %)
       (map (partial / numdocs) appearances)))

(defn multiply-out-tfidf
  "Just multiplies vectors, but the point here is:
  idf vector + row of term frequencies -> row of tf/idf scores
  (not testing this because that would be overkill)"
  [idfs tf-row]
  (mapv * idfs tf-row))

(defn make-tfidf
  "seq of seqs of tokens -> tfidf scores in tdm"
  [docs]
  (let [[header & data] (tdm/make-tdm docs)
        appearances (colsums (binary-token-presence data)) ; number of documents in which each term appears
        numdocs (count data)
        tfs (mapv term-frequencies data)
        idfs (inverse-doc-frequencies numdocs appearances)
        tfidf-scores (mapv (partial multiply-out-tfidf idfs) tfs)]; idf for each term
    (reduce conj [header] tfidf-scores)))
