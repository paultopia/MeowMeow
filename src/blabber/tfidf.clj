;; calculate tf-idf scores from tdm as defined by td-matrix in the tdm namespace
;; and just return labelled matrix of tf-idf scores
;;
;; supplies a normalized version (default) and an un-normalized version. 
;;
;; Normalized: 
;; tf = (/ (count of token T in document D) (number of tokens in D))
;; 
;; un-normalized (raw):
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


;; each row requires single element of rowsums and each element of column sums.
;; each item in row is function of rowsums[row] and columnsum[i]

; outer level just calculates the rowsums, colsums, and count
; level 2 loops over rows with loop-recur, passing in the first element of rowsums
; (plus colsums, plus count)
; and then loops with the rest.
; level 3 maps over a row, plus the vector of column sums

(ns blabber.tfidf)



(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))
;; it's really annoying that this isn't in clojure.core
;; see http://stackoverflow.com/questions/3249334/test-whether-a-list-contains-a-specific-value-in-clojure

; (defn- item-normalized
;   [count-t num-tokens numdocs numdocs-t]
;   (let [tf (float (/ count-t num-tokens)) idf (Math/log (/ numdocs numdocs-t))]
;     (* tf idf)))

(defn- item-normalized
  [count-t num-tokens numdocs numdocs-t]
  (let [tf (/ count-t num-tokens) idf (/ numdocs numdocs-t)]
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
  [column]
  (count (filter #(< 0 %) column)))

(defn- get-numdocs-tokens
  "this is the count of documents in which a given token appears (columnvec)"
  [tdm]
  (let [transpose (apply mapv vector tdm)]
    (mapv numdocs-t-helper transpose)))

(defn- level-three
  "function to map over items"
  [kw numdocs numtokens item column-item]
  (if (= kw :normalized)
    (item-normalized item numtokens numdocs column-item)
    (item-raw item numtokens numdocs column-item)))

(defn- level-two
  "function to map over rows"
  [kw colvec numdocs row numtokens]
  (map (partial level-three kw numdocs numtokens) row colvec))

(defn- level-one
  "function that maps over rows"
  [kw tdm]
  (map (partial level-two kw (get-numdocs-tokens tdm) (count tdm)) tdm (get-numtokens-rows tdm)))


(defn tfidf
  ([tdm]
    (tfidf tdm :normalized))
  ([tdm flag]
   (if 
    (= flag :raw) 
    	(level-one :raw tdm)
    	(level-one :normalized tdm))))


(def testmat [[0 1 2] [3 4 5] [6 7 8]])
