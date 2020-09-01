(ns gilded-rose-clojure.helper-test
  (:require [clojure.test :refer :all]
            [gilded-rose-clojure.helpers :refer :all]
            ))


(deftest dec-keep-positive-1-goes-to-0
  (testing "dec-keep-positive 1 goes to 0"
    (is (= 0 (dec-keep-positive 1)))
    ))

(deftest dec-keep-positive-0-stays-at-0
  (testing "dec-keep-positive 0 stays at 0"
    (is (= 0 (dec-keep-positive 0)))
    ))

(deftest inc-keep-below-tests
  (testing "inc-keep-below 1 with max of 50 goes to 2"
    (is (= 2 (inc-keep-below 50 1)))
    ))

(deftest inc-keep-below-2-max-of-2-stays-at-max
  (testing "inc-keep-below 2 with max of 2 stays at 2"
    (is (= 2 (inc-keep-below 2 2)))
    ))