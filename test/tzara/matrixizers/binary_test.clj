(ns tzara.matrixizers.binary-test
  (:require [tzara.matrixizers.binary :as binary]
            [clojure.test :refer :all]))

(def testvec ["cat" "dog" "woof" "meow" "fish" "glub" "glub" "glib" "glub" "glob" "glub" "meow" "tweet" "bird" "glub"])

(def testnest [testvec ["foo" "bar" "baz" "meow" "meow" "bat"]])


(deftest binary-token-presence
  (testing "binary token presence is correct"
    (is
     (=
      [[0 0 0 1 1 1 1 0 1 1 1 1 1 1] [1 1 1 0 0 0 0 1 0 0 0 1 0 0]]
      (binary/binary-token-presence [[0 0 0 1 1 1 1 0 1 1 5 2 1 1] [1 1 1 0 0 0 0 1 0 0 0 2 0 0]])))))

(deftest make-binary-tdm
  (testing "binary tdm production"
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
       [0 0 0 1 1 1 1 0 1 1 1 1 1 1]
       [1 1 1 0 0 0 0 1 0 0 0 1 0 0]]
      (binary/make-binary-tdm testnest)))))
