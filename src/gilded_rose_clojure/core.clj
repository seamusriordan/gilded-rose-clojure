(ns gilded-rose-clojure.core
  (:require [gilded-rose-clojure.helpers :refer :all]
            [gilded-rose-clojure.standard-item :as standard-item]
            [gilded-rose-clojure.brie :as brie]
            [gilded-rose-clojure.backstage :as backstage]
            [gilded-rose-clojure.conjured :as conjured]
            [gilded-rose-clojure.sulfuras :as sulfuras]))


(def exact-name-items
  {"Aged Brie"                                 :aged-brie,
   "Backstage passes to a TAFKAL80ETC concert" :backstage-pass,
   "Sulfuras, Hand of Ragnaros"                :sulfuras}
  )

(defn get-item-identifier
  [{:keys [name]}]
  (cond
    (exact-name-items name) (exact-name-items name)
    (re-find #"Conjured" name) :conjured
    :else :default
    ))

(defmulti quality-modifier get-item-identifier)
(defmethod quality-modifier :default [{:keys [sell-in quality]}] (standard-item/quality-modifier sell-in quality))
(defmethod quality-modifier :aged-brie [{:keys [sell-in quality]}] (brie/quality-modifier sell-in quality))
(defmethod quality-modifier :backstage-pass [{:keys [sell-in quality]}] (backstage/quality-modifier sell-in quality))
(defmethod quality-modifier :conjured [{:keys [sell-in quality]}] (conjured/quality-modifier sell-in quality))
(defmethod quality-modifier :sulfuras [{:keys [sell-in quality]}] (sulfuras/quality-modifier sell-in quality))

(defmulti sell-in-modifier get-item-identifier)
(defmethod sell-in-modifier :default [{:keys [sell-in]}] (standard-item/sell-in-modifier sell-in))
(defmethod sell-in-modifier :sulfuras[{:keys [sell-in]}] (sulfuras/sell-in-modify sell-in))

(defn update-item-quality
  [item]
  (assoc item :quality (quality-modifier item))
  )

(defn update-item-sell-in
  [item]
  (assoc item :sell-in (sell-in-modifier item))
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


