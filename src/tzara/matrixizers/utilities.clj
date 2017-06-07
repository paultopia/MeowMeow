(ns tzara.matrixizers.utilities
  "Public functions:

  make-token-list.
  generate vec of tokens to serve as header of csv.
  Takes either seq of frequency maps or seq of token vectors.  This is a new namespace and duplicates earlier functionality, but better, meant to replace earlier functionality (06/17)

  concatenate-header-and-data
  takes vec of headers (token labels) and nested vec of vecs of data (i.e., a dtm with counts or tf-idf scores or whatever) and combines them.
  CURRENTLY SUPER INEFFICIENT, appends to front of vector. Should optimize. Pulled out (6/17) from individual matrixizers to a utility function for later optimization."
  (:require [clojure.set :refer [union]]))

(defprotocol Uniqueizer
  "produce a sorted set of unique tokens from argument"
  (uniques [x] "unique tokens in x"))

(extend-protocol Uniqueizer
  clojure.lang.PersistentVector
  (uniques [x] (apply sorted-set x))
  clojure.lang.PersistentList
  (uniques [x] (apply sorted-set x))
  clojure.lang.LazySeq
  (uniques [x] (apply sorted-set x))
  clojure.lang.PersistentTreeMap
  (uniques [x] (apply sorted-set (keys x)))
  clojure.lang.PersistentArrayMap
  (uniques [x] (apply sorted-set (keys x)))
  clojure.lang.PersistentHashMap
  (uniques [x] (apply sorted-set (keys x))))

(defn make-token-list
  "seq of seqs of tokens or seq of frequency maps --> sorted vec of unique tokens.  Appears to preserve sorted set from uniques call (PersistentTreeSet vs PersistentHashSet) without casting to sorted set, but not sure if I can rely on this implementation detail.  Need to check docs."
  [s]
  (vec (reduce union (map uniques s))))

(defn concatenate-header-and-data
"FIX ME TO BE LESS INEFFICIENT!"
  [header data]
  (reduce conj [header] data))

