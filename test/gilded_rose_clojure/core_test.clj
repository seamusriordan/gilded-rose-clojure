(ns gilded-rose-clojure.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose-clojure.core :refer :all]
            [gilded-rose-clojure.helpers :refer :all]))

(deftest item-positive-sell-in-3-goes-down
  (testing "standard item sell in 3 goes to 2"
    (is (= 2
           (:sell-in (first (update-quality '({:name "Thing" :sell-in 3 :quality 7}))))
           ))
    ))

(deftest item-positive-sell-in-4-goes-down
  (testing "standard item sell in 3 goes to 2"
    (is (= 3
           (:sell-in (first (update-quality '({:name "Thing" :sell-in 4 :quality 7}))))
           ))
    ))

(deftest item-positive-sell-quality-7-goes-down
  (testing "standard item sell in 3, quality 7 goes to 6"
    (is (= 6
           (:quality (first (update-quality '({:name "Thing" :sell-in 1 :quality 7}))))
           ))
    ))

(deftest item-zero-sell-quality-7-goes-down
  (testing "standard item sell in 0, quality 7 goes to 5"
    (is (= 5
           (:quality (first (update-quality '({:name "Thing" :sell-in 0 :quality 7}))))
           ))
    ))

(deftest item-zero-quality-stays-positive
  (testing "item quality 0 stays positive"
    (is (= 0
           (:quality (first (update-quality '({:name "Thing" :sell-in 4 :quality 0}))))
           ))
    ))

(deftest brie-quality-3-goes-up
  (testing "brie quality 3 increments to 4"
    (is (= 4
           (:quality (first (update-quality '({:name "Aged Brie" :sell-in 4 :quality 3}))))
           )
        ))
  )

(deftest brie-quality-after-sell-in-goes-up-by-two
  (testing "brie quality 3 after sell-in increments goes to 5"
    (is (= 5
           (:quality (first (update-quality '({:name "Aged Brie" :sell-in -1 :quality 3}))))
           )
        ))
  )

(deftest brie-quality-is-maximum-50
  (testing "brie quality 50 stays at 50"
    (is (= 50
           (:quality (first (update-quality '({:name "Aged Brie" :sell-in 4 :quality 50}))))
           )
        ))
  )

(deftest sulfuras-quality-is-constant
  (testing "Sulfuras quality 80 does not change "
    (is (= 80
           (:quality (first (update-quality '({:name "Sulfuras, Hand of Ragnaros" :sell-in 4 :quality 80}))))
           ))

    ))

(deftest sulfuras-sell-in-quality-is-constant
  (testing "Sulfuras sell in 0 does not change"
    (is (= 0
           (:sell-in (first (update-quality '({:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}))))
           ))
    ))

(deftest backstage-sell-in-11-quality-3-goes-up-1
  (testing "backstage pass 11 days out goes up 1"
    (is (= 4
           (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 11 :quality 3}))))
           ))
    ))

(deftest backstage-sell-in-10-quality-3-goes-up-2
  (testing "backstage pass 10 days out goes up 2"
    (is (= 5
           (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 10 :quality 3}))))
           ))
    ))

(deftest backstage-sell-in-5-quality-3-goes-up-3
  (testing "backstage pass 5 days out goes up 3"
    (is (= 6
           (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 5 :quality 3}))))
           ))
    ))

(deftest backstage-sell-in-0-quality-goes-to-0
  (testing "backstage pass 0 days out goes to 0"
    (is (= 0
           (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in -3 :quality 3}))))
           ))
    ))


(deftest conjured-quality-drops-by-two
  (testing "Conjured breads quality drops by 2 10 days out"
    (is (= 1
           (:quality (first (update-quality '({:name "Conjured bread" :sell-in 10 :quality 3}))))
           ))
    ))

(deftest conjured-after-sell-in-quality-drops-by-four
  (testing "Conjured turnip quality drops by 4 2 days after sell in"
   (is (= 1
          (:quality (first (update-quality '({:name "Conjured turnip" :sell-in -2 :quality 5}))))
          ))
   ))

(deftest conjured-items-have-space-after-conjured
  (testing "Conjurededer quality drops by 1"
    (is (= 4
           (:quality (first (update-quality '({:name "Conjurededer" :sell-in 5 :quality 5}))))
           ))
    )
  )

