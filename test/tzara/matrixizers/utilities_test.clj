(ns tzara.matrixizers.utilities-test
  (:require [tzara.matrixizers.utilities :as utils]
            [clojure.test :refer :all]))

(def testvec ["cat" "dog" "woof" "meow" "fish" "glub" "glub" "glib" "glub" "glob" "glub" "meow" "tweet" "bird" "glub"])

(def testnest [testvec ["foo" "bar" "baz" "meow" "meow" "bat"]])

(deftest uniques-vector
  (testing "can get unique items from vector"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (utils/uniques testvec)))))

(deftest uniques-map
  (testing "can get unique items from frequency map"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (utils/uniques (frequencies testvec))))))

(deftest uniques-lazyseq
  (testing "can get unique items from lazy sequence"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (utils/uniques (map identity testvec))))))

(deftest alltokens
  (testing "can get tokens from nested vectors"
    (is
     (=
      ["bar" "bat" "baz" "bird" "cat" "dog" "fish" "foo" "glib" "glob" "glub" "meow" "tweet" "woof"]
      (utils/make-token-list testnest)))))


(deftest concatenate-header-and-data
  (testing "concatenation of header vec and data vec work as expected"
    (is
     (= [[:a :b :c] [1 2 3] [4 5 6]]
      (utils/concatenate-header-and-data [:a :b :c] [[1 2 3] [4 5 6]])))))
