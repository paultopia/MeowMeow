(ns tzara.matrixizers.tfidf
  "generate dtm of tf-idf scores. Public function: make-tfidf

  tfidf calculation based on http://www.tfidf.com

  NOTE: default version calculates term frequencies normalized by total length of document---other people (e.g. https://nlp.stanford.edu/IR-book/html/htmledition/term-frequency-and-weighting-1.html) calculate term frequency without normalization.

In order to get non-normalized version, pass :raw into the function, i.e.,
  (make-tfidf docs :raw)

  You can also normalize by number of unique terms in document rather than total length with:
  (make-tfidf docs :unique-normalized)

  Only the default normalization is tested, however, the difference is just swapping out the divisor in the tf-idf function, so should be ok.
  "
  (:require [tzara.matrixizers.tdm :as tdm]))

(defn colsums
  "sum columns of multidimensional seq"
  [s]
  (apply (partial mapv +) s))

(defn binary-token-presence
  "replace tdm rows (seq of seq of tokens) with 1 if in dataset or 0 if not. (might pull out as separate representation actually)"
  [s]
  (mapv #(mapv (partial min 1) %) s))

(defn total-normalized-term-frequencies
  "calculate term frequencies in row (seq of term counts)"
  [row]
  (let [doc-length-repeated (repeat (apply + row))]
    (mapv / row doc-length-repeated)))

(defn unique-normalized-term-frequencies
  "calculate term frequencies in row (seq of term counts)"
  [row]
  (let [unique-terms-in-doc-repeated (repeat (apply + (mapv (partial min 1) row)))]
    (mapv / row unique-terms-in-doc-repeated)))


(defn inverse-doc-frequencies [numdocs appearances]
  (mapv #(Math/log %)
       (map (partial / numdocs) appearances)))

(defn multiply-out-tfidf
  "Just multiplies vectors, but the point here is:
  idf vector + row of term frequencies -> row of tf/idf scores
  (not testing this because that would be overkill)"
  [idfs tf-row]
  (mapv * idfs tf-row))

(defn normalization [option data]
  (let [norm-func (get
                   {:total-normalized total-normalized-term-frequencies
                    :unique-normalized unique-normalized-term-frequencies
                    :raw false} option)]
    (if norm-func (mapv norm-func data) data)))

(defn make-tfidf
  "seq of seqs of tokens -> tfidf scores in tdm"
  ([docs] (make-tfidf docs :total-normalized))
  ([docs option]
   (let [[header & data] (tdm/make-tdm docs)
         appearances (colsums (binary-token-presence data)) ; number of documents in which each term appears
         numdocs (count data)
         tfs (normalization option data)
         idfs (inverse-doc-frequencies numdocs appearances)
         tfidf-scores (mapv (partial multiply-out-tfidf idfs) tfs)]; idf for each term
     (reduce conj [header] tfidf-scores))))
