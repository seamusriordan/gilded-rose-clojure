(ns gilded-rose-clojure.helper-test
  (:require [clojure.test :refer :all]
            [gilded-rose-clojure.helpers :refer :all]
            ))


(deftest dec-keep-positive-tests
  (testing "dec-keep-positive"
    (testing "1 goes to 0"
      (is (= 0 (dec-keep-positive 1))))
    (testing "0 stays at 0"
      (is (= 0 (dec-keep-positive 0))))
    ))

(deftest inc-keep-below-tests
  (testing "inc-keep-below"
    (testing "1 max of 50 goes to 2"
      (is (= 2 (inc-keep-below 50 1))))
    (testing "2 max of 2 stays at 2"
      (is (= 2 (inc-keep-below 2 2))))
    ))