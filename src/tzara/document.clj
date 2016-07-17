(ns tzara.document
"namespace for document-level/wide transformations: word and char-tokenizers and 
all-doc-character level transformations like removing punctuation.  All 
functions here assume document is a single string, and are straightforwardly 
mappable to entire dataset of docs"
    (:require
    [clojure.string :refer [lower-case split]]))

(def tolower lower-case)
;; why?  because R and C use that and I don't want to memorize an extra name

(defn depunctuate
  "strip punctuation from document"
  [document]
  (apply str (filter #(or (Character/isLetter %) (Character/isDigit %) (Character/isSpace %)) document)))

(defn denumber
  "strip numbers from document"
  [document]
  (apply str (remove #(Character/isDigit %) document)))

(defn tokenize
  "split document into tokens by whitespace"
  [document]
  (split document #"\s"))

(defn char-ngram
  "split document into tokens as character level ngrams of size n, including spaces 
  (why? see http://www.aclweb.org/anthology/N15-1010 )"
  [strng n]
  (mapv #(apply str %) (vec (partition n 1 (vec strng)))))

;; do I need a punctuation tokenizer too?
