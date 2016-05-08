(ns MeowMeow.core-test
  (:require [clojure.test :refer :all]
            [MeowMeow.core :as cor]
            [MeowMeow.tdm :as tdm]
            [MeowMeow.tokens :as tok]
            [MeowMeow.document :as doc]
            [MeowMeow.tfidf :as tfi]
            [MeowMeow.utilities :as uti]
            [MeowMeow.sparsity :as spa]
            [MeowMeow.dsops :as dso]))

(def testtokens [["One" "Two" "Three"] ["Ay" "Bee" "Cee"] ["One" "Ay" "One" "Bee" "One" "Ay"]])

(deftest tdmap 
  (testing "can generate td-map from matrix of tokens"
    (is (= [{"One" 1, "Two" 1, "Three" 1, "Ay" 0, "Bee" 0, "Cee" 0} {"Ay" 1, "Bee" 1, "Cee" 1, "One" 0, "Two" 0, "Three" 0} {"One" 3, "Ay" 2, "Bee" 1, "Two" 0, "Three" 0, "Cee" 0}]
           (tdm/td-maps testtokens)))))

(deftest tdmatrix 
  (testing "can generate td-matrix from matrix of tokens"
    (is (= {:labels ["Ay" "Bee" "Cee" "One" "Three" "Two"], :frequencies [[0 0 0 1 1 1] [1 1 1 0 0 0] [2 1 0 3 0 0]]}
           (tdm/td-matrix (MeowMeow.tdm/td-maps testtokens))))))


(deftest basic-ngram
  (testing "can generate ngrams from a vector of strings"
    (is (= ["catdog" "dogmeow" "meowwoof" "woofkitty"] 
           (tok/ngram ["cat" "dog" "meow" "woof" "kitty"] 2)))))

(deftest character-ngram
  (testing "can generate character ngrams from a string"
    (is (= ["te" "e," ", " " s" "st"] 
           (doc/char-ngram "te, st" 2)))))

(deftest tfidf-calc
  (testing "can calculate normalized tf-idf scores"
    (is (= [[0.0 0.0 0.0] [0.1013662770270411 0.0 0.0] [0.11584717892396205 0.0 0.0]] 
           (tfi/tfidf [[0 1 2] [3 4 5] [6 7 8]])))))

(deftest tfidf-raw
  (testing "can calculate raw tf-idf scores"
    (is (= [[0.0 0.0 0.0] [1.2163953243244932 0.0 0.0] [2.4327906486489863 0.0 0.0]] 
           (tfi/tfidf [[0 1 2] [3 4 5] [6 7 8]] :raw)))))

(deftest stopwords-default
  (testing "can remove default stopwords"
    (is (= ["kitty" "meow"] 
           (tok/remove-stopwords ["this" "is" "a" "kitty" "meow"])))))

(deftest stopwords-custom
  (testing "can remove custom stopwords"
    (is (= ["this" "is" "a" "meow"] 
           (tok/remove-stopwords ["this" "is" "a" "kitty" "meow"] #{"kitty"})))))

(deftest sparsity
  (testing "can remove uncommon tokens"
    (is (= [[1 1] [2 3] [2 3]] 
           (spa/filter-sparsity [[0 1 1] [1 2 3] [1 2 3]] 0.9)))))

(def test-dataset {:labels ["Ay" "Bee" "Cee" "One" "Three" "Two"], :frequencies [[0 0 0 1 1 1] [1 1 1 0 0 0] [2 1 0 3 0 0]]})
(def test-merger {:labels ["Kitty" "Meow" "Purr" "Dogs" "Are" "Inferior"], :frequencies [[0 55 0 1 1 1] [3 1 1 0 0 0] [2 1 9 9 0 0]]})

(deftest combines
  (testing "can combine datasets"
    (is (= {:frequencies [[0 0 0 1 1 1 0 55 0 1 1 1] [1 1 1 0 0 0 3 1 1 0 0 0] [2 1 0 3 0 0 2 1 9 9 0 0]],
            :labels ["Ay" "Bee" "Cee" "One" "Three" "Two" "Kitty" "Meow" "Purr" "Dogs" "Are" "Inferior"]} 
          (dso/merge-data test-dataset test-merger)))))

(deftest tfidf-datasets
  (testing "can calculate tf-idf scores on datasets"
    (is (= {:frequencies [[0.0 0.0 0.0] [0.1013662770270411 0.0 0.0] [0.11584717892396205 0.0 0.0]] :labels [:a :b :c]} 
           (dso/tfidf {:labels [:a :b :c] :frequencies [[0 1 2] [3 4 5] [6 7 8]]})))))


