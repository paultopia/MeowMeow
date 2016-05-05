(ns MeowMeow.core-test
  (:require [clojure.test :refer :all]
            [MeowMeow.core]
            [MeowMeow.tdm]
            [MeowMeow.tokens]
            [MeowMeow.document]
            [MeowMeow.tfidf]
            [MeowMeow.utilities]))




;; this is all tested and correct, albeit in clojurescript rather than clojure...

;; (def testdocs [["One" "Two" "Three"] ["Ay" "Bee" "Cee"] ["One" "Ay" "One" "Bee" "One" "Ay"]])
;; (td-maps testdocs)
;; [{"One" 1, "Two" 1, "Three" 1, "Ay" 0, "Bee" 0, "Cee" 0} {"Ay" 1, "Bee" 1, "Cee" 1, "One" 0, "Two" 0, "Three" 0} {"One" 3, "Ay" 2, "Bee" 1, "Two" 0, "Three" 0, "Cee" 0}]
;; (td-matrix (td-maps testdocs))
;; {:labels ("Ay" "Bee" "Cee" "One" "Three" "Two"), :frequencies [(0 0 0 1 1 1) (1 1 1 0 0 0) (2 1 0 3 0 0)]}

(def testtokens [["One" "Two" "Three"] ["Ay" "Bee" "Cee"] ["One" "Ay" "One" "Bee" "One" "Ay"]])

(deftest tdmap 
  (testing "can generate td-map from matrix of tokens"
    (is (= [{"One" 1, "Two" 1, "Three" 1, "Ay" 0, "Bee" 0, "Cee" 0} {"Ay" 1, "Bee" 1, "Cee" 1, "One" 0, "Two" 0, "Three" 0} {"One" 3, "Ay" 2, "Bee" 1, "Two" 0, "Three" 0, "Cee" 0}]
           (MeowMeow.tdm/td-maps testtokens)))))

(deftest tdmatrix 
  (testing "can generate td-matrix from matrix of tokens"
    (is (= {:labels '("Ay" "Bee" "Cee" "One" "Three" "Two"), :frequencies ['(0 0 0 1 1 1) '(1 1 1 0 0 0) '(2 1 0 3 0 0)]}
           (MeowMeow.tdm/td-matrix (MeowMeow.tdm/td-maps testtokens))))))


(deftest basic-ngram
  (testing "can generate ngrams from a vector of strings"
    (is (= ["catdog" "dogmeow" "meowwoof" "woofkitty"] (MeowMeow.tokens/ngram ["cat" "dog" "meow" "woof" "kitty"] 2)))))

(deftest character-ngram
  (testing "can generate character ngrams from a string"
    (is (= ["te" "e," ", " " s" "st"] (MeowMeow.document/char-ngram "te, st" 2)))))

(deftest tfidf-calc
  (testing "can calculate normalized tf-idf scores"
    (is (= [[0.0 0.0 0.0] [0.1013662770270411 0.0 0.0] [0.11584717892396205 0.0 0.0]] (MeowMeow.tfidf/tfidf [[0 1 2] [3 4 5] [6 7 8]])))))

(deftest tfidf-raw
  (testing "can calculate raw tf-idf scores"
    (is (= [[0.0 0.0 0.0] [1.2163953243244932 0.0 0.0] [2.4327906486489863 0.0 0.0]] (MeowMeow.tfidf/tfidf [[0 1 2] [3 4 5] [6 7 8]] :raw)))))

