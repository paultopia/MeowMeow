;; calculate tf-idf scores from tdm as defined by td-matrix in the tdm namespace
;; and just return labelled matrix of tf-idf scores
;;
;; supplies a normalized version (default) and an un-normalized version. 
;;
;; Normalized: 
;; tf = (/ (count of token T in document D) (number of tokens in D))
;; 
;; Nn-normalized (raw):
;; tf = (count of token T in document D)
;;
;; in all cases, 
;; idf = (ln (/ (count of documents) (count of documents containing T)))
;; tf-idf = (* tf idf)
;;
;; some people seem to think that TF should be normalized by number of 
;; unique tokens, but I don't imagine that makes a difference for 
;; practical purposes, if someone wants to implement that as an alternative 
;; I'll happily take a PR. 
;; 
;; api: for default, (tfidf YOUR-TDM), 
;; otherwise, (tfidf YOUR-TDM :normalized) or (tfidf YOUR-TDM :raw)



(ns blabber.tfidf)

(defn- item-normalized
  [count-t num-tokens numdocs numdocs-t]
  (let [tf (float (/ count-t num-tokens)) idf (Math/log (/ numdocs numdocs-t))]
    (* tf idf)))

(defn- item-raw
  [count-t _ numdocs numdocs-t]
  (let [idf (Math/log (/ numdocs numdocs-t))]
    (* count-t idf)))

(defn- get-numtokens-rows
  "number of tokens in a document"
  [tdm]
  (mapv #(apply + %) tdm))

(defn- numdocs-t-helper
  "given a column represented as a bunch of individual entries in that column, 
  (NOT as a vector) returns the number of entries that are above zero"
  [& args]
  (count (filter #(< 0 %) args)))

(defn- get-numdocs-tokens
  "this is the count of documents in which a given token appears (columnvec)
  ...
  note to self for future reference: this is the same sneaky trick that lets you 
  transpose a matrix by (apply map vector matrix) --- map when given n sequences 
  takes the first from each sequence, then the second from each sequence, etc. -- 
  so when given map applied over a sequence of rows, it's like mapping over 
  a sequence of columns without apply"
  [tdm]
  (apply map numdocs-t-helper tdm))


(defn- level-three
  "function to map over items"
  [kw numdocs numtokens item column-item]
  (if (= kw :normalized)
    (item-normalized item numtokens numdocs column-item)
    (item-raw item numtokens numdocs column-item)))

(defn- level-two
  "function to map over rows"
  [kw colvec numdocs row numtokens]
  (mapv (partial level-three kw numdocs numtokens) row colvec))

(defn- level-one
  "function that maps over rows"
  [kw tdm]
  (mapv (partial level-two kw (get-numdocs-tokens tdm) (count tdm)) tdm (get-numtokens-rows tdm)))


(defn tfidf
  ([tdm]
    (tfidf tdm :normalized))
  ([tdm flag]
   (if 
    (= flag :raw) 
    	(level-one :raw tdm)
    	(level-one :normalized tdm))))
