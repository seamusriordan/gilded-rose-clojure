(ns gilded-rose-clojure.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose-clojure.core :refer :all]
            [gilded-rose-clojure.helpers :refer :all]))

(deftest item-tests
  (testing "generic item"
    (testing "positive sell in"
      (testing "sell in 3 goes to 2"
        (is (= 2
               (:sell-in (first (update-quality '({:name "Thing" :sell-in 3 :quality 7}))))
               )))
      (testing "sell in 4 goes to 3"
        (is (= 3
               (:sell-in (first (update-quality '({:name "Thing" :sell-in 4 :quality 7}))))
               )))
      (testing "quality 7 goes to 6"
        (is (= 6
               (:quality (first (update-quality '({:name "Thing" :sell-in 1 :quality 7}))))
               ))))
    (testing "zero sell-in"
      (testing "quality in 7 goes to 5"
        (is (= 5
               (:quality (first (update-quality '({:name "Thing" :sell-in 0 :quality 7}))))
               )))
      )

    (testing "quality stays positive"
      (is (= 0
             (:quality (first (update-quality '({:name "Thing" :sell-in 4 :quality 0}))))
             )))
    ))

(deftest aged-brie-test
  (testing "aged brie"
    (testing "quality 3 increments to 4"
      (is (= 4
             (:quality (first (update-quality '({:name "Aged Brie" :sell-in 4 :quality 3}))))
             )
          ))
    (testing "quality 3 increments to 5 with sell in -1"
      (is (= 5
             (:quality (first (update-quality '({:name "Aged Brie" :sell-in -1 :quality 3}))))
             )
          ))
    (testing "quality 50 stays at 50"
      (is (= 50
             (:quality (first (update-quality '({:name "Aged Brie" :sell-in 4 :quality 50}))))
             )
          ))
    ))

(deftest sulfuras-test
  (testing "Sulfuras, Hand of Ragnaros"
    (testing "quality 80 does not change"
      (is (= 80
             (:quality (first (update-quality '({:name "Sulfuras, Hand of Ragnaros" :sell-in 4 :quality 80}))))
             ))
      )
    (testing "sell in 0 does not change"
      (is (= 0
             (:sell-in (first (update-quality '({:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}))))
             ))
      )
    ))

(deftest backstage-test
  (testing "backstage"
    (testing "quality 3 up 1 when 11 days out"
      (is (= 4
             (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 11 :quality 3}))))
             ))
      )
    (testing "quality 3 up 2 when 10 days out"
      (is (= 5
             (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 10 :quality 3}))))
             ))
      )
    (testing "quality 3 up 3 when 5 days out"
      (is (= 6
             (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 5 :quality 3}))))
             ))
      )
    (testing "quality 3 to 0 when 0 days out"
      (is (= 0
             (:quality (first (update-quality '({:name "Backstage passes to a TAFKAL80ETC concert" :sell-in -3 :quality 3}))))
             ))
      )
    ))

(deftest conjured-test
  (testing "Conjured"
    (testing "Conjured breads quality drops by 2 10 days out"
      (is (= 1
             (:quality (first (update-quality '({:name "Conjured bread" :sell-in 10 :quality 3}))))
             ))
      )
    (testing "Conjured turnip quality drops by 4 2 days after sell in"
      (is (= 1
             (:quality (first (update-quality '({:name "Conjured turnip" :sell-in -2 :quality 5}))))
             ))
      )
    ))

