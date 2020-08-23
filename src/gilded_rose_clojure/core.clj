(ns gilded-rose-clojure.core
  (:require [gilded-rose-clojure.helpers :refer :all]
            [gilded-rose-clojure.standard-item :as standard-item]
            [gilded-rose-clojure.brie :as brie]
            [gilded-rose-clojure.backstage :as backstage]
            [gilded-rose-clojure.conjured :as conjured]
            [gilded-rose-clojure.sulfuras :as sulfuras]))



(def exact-name-quality-modifiers
  {"Aged Brie"                                 brie/quality-modifier,
   "Backstage passes to a TAFKAL80ETC concert" backstage/quality-modifier,
   "Sulfuras, Hand of Ragnaros"                sulfuras/quality-modifier})

(def sell-in-modifiers
  {"Sulfuras, Hand of Ragnaros" sulfuras/sell-in-modify})

(defn get-quality-modifier
  [name]
  (let [exact-name-modifier (get exact-name-quality-modifiers name)]
    (cond
      exact-name-modifier exact-name-modifier
      (re-find #"Conjured" name) conjured/quality-modifier
      :else standard-item/quality-modifier)
    ))

(defn get-sell-in-modifier
  [name]
  (let [exact-name-modifier (get sell-in-modifiers name)]
    (cond
      exact-name-modifier exact-name-modifier
      :else standard-item/sell-in-modifier)
    ))

(defn update-item-quality
  [item]
  (let [sell-in (:sell-in item)
        quality-modifier (get-quality-modifier (:name item))]
    (update item :quality (partial quality-modifier sell-in))))

(defn update-item-sell-in
  [item]
  (let [name (:name item)
        sell-in-modifier (get-sell-in-modifier name)]
    (update item :sell-in sell-in-modifier))
  )

(defn update-item
  [item]
  (-> item
      update-item-quality
      update-item-sell-in)
  )

(defn update-quality
  [items]
  (map update-item items))

(defn -main
  [& _]
  (let [data [
              {:name "+5 Dexterity Vest" :sell-in 10 :quality 20}
              {:name "Aged Brie" :sell-in 2 :quality 0}
              {:name "Elixir of the Mongoose" :sell-in 5 :quality 7}
              {:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}
              {:name "Sulfuras, Hand of Ragnaros" :sell-in -1 :quality 80}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 15 :quality 20}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 10 :quality 49}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 5 :quality 49}
              {:name "Conjured Mana Cake" :sell-in 3 :quality 6}
              ]
        updated-data (update-quality data)]
    (doall (map println data))
    (println)
    (doall (map println updated-data))
    )
  )


