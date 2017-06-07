(ns tzara.matrixizers.utilities-test
  (:require [tzara.matrixizers.utilities :as ut]
            [clojure.test :refer :all]))

(def testvec ["cat" "dog" "woof" "meow" "fish" "glub" "glub" "glib" "glub" "glob" "glub" "meow" "tweet" "bird" "glub"])

(def testnest [testvec ["foo" "bar" "baz" "meow" "meow" "bat"]])

(deftest uniques-vector
  (testing "can get unique items from vector"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (ut/uniques testvec)))))

(deftest uniques-map
  (testing "can get unique items from frequency map"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (ut/uniques (frequencies testvec))))))

(deftest uniques-lazyseq
  (testing "can get unique items from lazy sequence"
    (is
     (=
      #{"bird" "cat" "dog" "fish" "glib" "glob" "glub" "meow" "tweet" "woof"}
      (ut/uniques (map identity testvec))))))

(deftest alltokens
  (testing "can get tokens from nested vectors"
    (is
     (=
      ["bar" "bat" "baz" "bird" "cat" "dog" "fish" "foo" "glib" "glob" "glub" "meow" "tweet" "woof"]
      (ut/make-token-list testnest)))))
