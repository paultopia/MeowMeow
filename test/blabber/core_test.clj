(ns blabber.core-test
  (:require [clojure.test :refer :all]
            [blabber.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

; obsolete the moment it's written.

(deftest basic-matrix
  (testing "Can compute basic TDM without transformations."
    (is (= [[:cat :is :this :words :dog :and :meow :woof :a] [1 1 1 0 0 0 0 0 1] [0 1 1 0 1 0 0 0 1] [0 0 0 0 0 1 1 1 1] [0 0 0 1 0 0 2 3 0]] 
           (make-TD-matrix ["this is a cat" "this is a dog" "woof and a meow" "woof woof woof meow meow words"])))))