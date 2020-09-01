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
    (re-find #"Conjured\s" name) :conjured
    :else :default
    ))

(defmulti get-updated-quality get-item-identifier)
(defn setup-quality-modifier
  [key f]
  (defmethod get-updated-quality key [{:keys [sell-in quality]}] (f sell-in quality)))
(setup-quality-modifier :default standard-item/quality-modifier)
(setup-quality-modifier :aged-brie brie/quality-modifier)
(setup-quality-modifier :backstage-pass backstage/quality-modifier)
(setup-quality-modifier :conjured conjured/quality-modifier)
(setup-quality-modifier :sulfuras sulfuras/quality-modifier)

(defmulti get-updated-sell-in get-item-identifier)
(defn setup-sell-in-modifier
  [key f]
  (defmethod get-updated-sell-in key [{:keys [sell-in]}] (f sell-in)))
(setup-sell-in-modifier :default standard-item/sell-in-modifier)
(setup-sell-in-modifier :sulfuras sulfuras/sell-in-modifier)

(defn update-item-quality
  [item]
  (assoc item :quality (get-updated-quality item))
  )

(defn update-item-sell-in
  [item]
  (assoc item :sell-in (get-updated-sell-in item))
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
  (let [data [{:name "+5 Dexterity Vest" :sell-in 10 :quality 20}
              {:name "Aged Brie" :sell-in 2 :quality 0}
              {:name "Elixir of the Mongoose" :sell-in 5 :quality 7}
              {:name "Sulfuras, Hand of Ragnaros" :sell-in 0 :quality 80}
              {:name "Sulfuras, Hand of Ragnaros" :sell-in -1 :quality 80}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 15 :quality 20}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 10 :quality 49}
              {:name "Backstage passes to a TAFKAL80ETC concert" :sell-in 5 :quality 49}
              {:name "Conjured Mana Cake" :sell-in 3 :quality 6}]
        updated-data (update-quality data)]
    (doall (map println data))
    (println)
    (doall (map println updated-data))
    )
  )


