(ns tzara.tokens
"namespace for transformations that operate on document as vector of tokens, 
including transformations like n-gram re-tokenization that assume prior 
tokenization.  (inefficient I suppose), also including stemmers, stopword 
removal.  all fxns mappable to entire dataset of tokenized docs"
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

(def default-stopwords #{"i", "me", "my", "myself", "we", "our", "ours", 
                         "ourselves", "you", "your", "yours", "yourself", 
                         "yourselves", "he", "him", "his", "himself", 
                         "she", "her", "hers", "herself", "it", "its", 
                         "itself", "they", "them", "their", "theirs", 
                         "themselves", "what", "which", "who", "whom", 
                         "this", "that", "these", "those", "am", "is", 
                         "are", "was", "were", "be", "been", "being", 
                         "have", "has", "had", "having", "do", "does", 
                         "did", "doing", "a", "an", "the", "and", "but", 
                         "if", "or", "because", "as", "until", "while", 
                         "of", "at", "by", "for", "with", "about", 
                         "against", "between", "into", "through", "during", 
                         "before", "after", "above", "below", "to", "from", 
                         "up", "down", "in", "out", "on", "off", "over", 
                         "under", "again", "further", "then", "once", "here", 
                         "there", "when", "where", "why", "how", "all", "any", 
                         "both", "each", "few", "more", "most", "other", 
                         "some", "such", "no", "nor", "not", "only", "own", 
                         "same", "so", "than", "too", "very", "s", "t", "can", 
                         "will", "just", "don", "should", "now"})
;; these default stopwords are just the english stopwords from the python NLTK library.

(defn remove-stopwords
  "takes stopwords as a set.  removes them."
  ([tokenvec]
   (remove-stopwords tokenvec default-stopwords))
  ([tokenvec stopwords]
   (vec (filter (complement stopwords) tokenvec))))

