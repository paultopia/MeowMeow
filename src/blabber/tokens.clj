;; namespace for transformations that operate on document as vector of tokens, 
;; including transformations like n-gram re-tokenization that assume prior 
;; tokenization.  (inefficient I suppose), also including stemmers. 
;; all fxns mappable to entire dataset of tokenized docs

(ns blabber.tokens
  (:require
    [stemmer.snowball :as snowball]))


(defn ngram
  "re-tokenize word-tokenized string or char strvec into 
  word-level n-grams of size n (no spaces)"
  [tokenvec n]
  (mapv #(apply str %) (partition n 1 tokenvec)))

(defn snow-stem 
  "use snowball stemmer to stem in a given language, passed in as :keyword
  (todo: allow passing in as string too)"
  [language tokenvec]
  (let [stemmer (snowball/stemmer language)]
    (mapv stemmer tokenvec)))