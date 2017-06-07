(ns tzara.matrixizers.tfidf-test
  (:require  [clojure.test :refer :all]
             [tzara.matrixizers.tfidf :as tfidf]))

(def testvec ["cat" "dog" "woof" "meow" "fish" "glub" "glub" "glib" "glub" "glob" "glub" "meow" "tweet" "bird" "glub"])

(def testnest [testvec ["foo" "bar" "baz" "meow" "meow" "bat"]])

(deftest colsums
  (testing "column sums are correct"
    (is
     (=
      [12 15 18]
      (tfidf/colsums [[1 2 3] [4 5 6] [7 8 9]])))))

(deftest binary-token-presence
  (testing "binary token presence is correct"
    (is
     (=
      [[0 0 0 1 1 1 1 0 1 1 1 1 1 1] [1 1 1 0 0 0 0 1 0 0 0 1 0 0]]
      (tfidf/binary-token-presence [[0 0 0 1 1 1 1 0 1 1 5 2 1 1] [1 1 1 0 0 0 0 1 0 0 0 2 0 0]])))))


(deftest total-normalized-term-frequencies
  (testing "total normalized term frequencies (the tf in tf-idf) is correct"
    (is
     (=
      [3/8 5/8]
      (tfidf/total-normalized-term-frequencies [3 5])))))

(deftest unique-normalized-term-frequencies
  (testing "unique normalized term frequencies (the tf in tf-idf) is correct"
    (is
     (=
      [0 0 1/4 1/2 3/4 1]
      (tfidf/unique-normalized-term-frequencies [0 0 1 2 3 4])))))


(deftest inverse-doc-frequencies
  (testing "inverse document frequencies (the idf in tf-idf) is correct"
    (is
     (=
      [1.6094379124341003 0.9162907318741551 0.0]
      (tfidf/inverse-doc-frequencies 5 [1 2 5])))))

(deftest make-tfidf
  (testing "make tfidf scores"
    (is
     (=
      [["bar"
        "bat"
        "baz"
        "bird"
        "cat"
        "dog"
        "fish"
        "foo"
        "glib"
        "glob"
        "glub"
        "meow"
        "tweet"
        "woof"]
       [0.0
        0.0
        0.0
        0.046209812037329684
        0.046209812037329684
        0.046209812037329684
        0.046209812037329684
        0.0
        0.046209812037329684
        0.046209812037329684
        0.23104906018664842
        0.0
        0.046209812037329684
        0.046209812037329684]
       [0.11552453009332425
        0.11552453009332425
        0.11552453009332425
        0.0
        0.0
        0.0
        0.0
        0.11552453009332425
        0.0
        0.0
        0.0
        0.0
        0.0
        0.0]]
      (tfidf/make-tfidf testnest)))))

