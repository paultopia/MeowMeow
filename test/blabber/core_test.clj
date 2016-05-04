(ns blabber.core-test
  (:require [clojure.test :refer :all]
            [blabber.core :refer :all]))


; this next test is obsolete because I've mucked with the api the moment it's written.
; also blabber.core needs to be fixed.

(deftest basic-matrix
  (testing "Can compute basic TDM without transformations."
    (is (= [[:cat :is :this :words :dog :and :meow :woof :a] [1 1 1 0 0 0 0 0 1] [0 1 1 0 1 0 0 0 1] [0 0 0 0 0 1 1 1 1] [0 0 0 1 0 0 2 3 0]]
           (make-TD-matrix ["this is a cat" "this is a dog" "woof and a meow" "woof woof woof meow meow words"])))))

(deftest basic-ngram
  (testing "can generate ngrams from a vector of strings"
    (is (= ["catdog" "dogmeow" "meowwoof" "woofkitty"] (ngram ["cat" "dog" "meow" "woof" "kitty"] 2)))))

(deftest character-ngram
  (testing "can generate character ngrams from a string"
    (is (= ["te" "e," ", " " s" "st"] (char-ngram "te, st" 2)))))

(deftest tfidf-calc
  (testing "can calculate normalized tf-idf scores"
    (is (= [[0.0 0.0 0.0] [0.1013662770270411 0.0 0.0] [0.11584717892396205 0.0 0.0]] (tfidf [[0 1 2] [3 4 5] [6 7 8]])))))

(deftest tfidf-raw
  (testing "can calculate raw tf-idf scores"
    (is (= [[0.0 0.0 0.0] [1.2163953243244932 0.0 0.0] [2.4327906486489863 0.0 0.0]] (tfidf [[0 1 2] [3 4 5] [6 7 8]] :raw)))))

