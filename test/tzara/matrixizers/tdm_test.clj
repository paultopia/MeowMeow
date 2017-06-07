(ns tzara.matrixizers.tdm-test
  (:require [tzara.matrixizers.tdm :as tdm]
            [tzara.matrixizers.utilities :as utils]
            [clojure.test :refer :all]))

(def testvec ["cat" "dog" "woof" "meow" "fish" "glub" "glub" "glib" "glub" "glob" "glub" "meow" "tweet" "bird" "glub"])

(def testnest [testvec ["foo" "bar" "baz" "meow" "meow" "bat"]])

(def all-tokens (utils/make-token-list testnest))

(deftest make-row
  (testing "row vec is correct"
    (is
     (=
      [0 0 0 1 1 1 1 0 1 1 5 2 1 1]
      (tdm/make-row all-tokens testvec)))))

(deftest make-tdm
  (testing "tdm is correct"
    (is
     (=
      "bar,bat,baz,bird,cat,dog,fish,foo,glib,glob,glub,meow,tweet,woof\n0,0,0,1,1,1,1,0,1,1,5,2,1,1\n1,1,1,0,0,0,0,1,0,0,0,2,0,0\n"
      (tdm/make-tdm testnest)))))

