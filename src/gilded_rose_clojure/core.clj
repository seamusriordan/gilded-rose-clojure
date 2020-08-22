(ns gilded-rose-clojure.core
  (:require [gilded-rose-clojure.helpers :refer :all])
  )

(defn apply-sell-in-based-quality-modifier
  [modifier sell-in quality]
  (if (> sell-in 0)
    (apply-n-times modifier 1 quality)
    (apply-n-times modifier 2 quality)
    )
  )

(defn generic-item-quality-modify
  [sell-in quality]
  (apply-sell-in-based-quality-modifier dec-keep-positive sell-in quality)
  )

(defn brie-quality-modify
  [sell-in quality]
  (let [inc-keep-below-50 (partial inc-keep-below 50)]
    (apply-sell-in-based-quality-modifier inc-keep-below-50 sell-in quality)
    )
  )

(defn backstage-quality-modify
  [sell-in quality]
  (
    let [max-quality 50
         inc-keep-below-50 (partial inc-keep-below max-quality)
         inc-quality-n-times #(apply-n-times inc-keep-below-50 % quality)
         ]
    (cond
      (> sell-in 10) (inc-quality-n-times 1)
      (is-in-inclusive-range 6 10 sell-in) (inc-quality-n-times 2)
      (is-in-inclusive-range 1 5 sell-in) (inc-quality-n-times 3)
      :else 0)
    ))

(defn conjured-quality-modify
  [sell-in quality]
  (let [dec-twice (comp dec-keep-positive dec-keep-positive)]
    (apply-sell-in-based-quality-modifier dec-twice sell-in quality))
  )

(def exact-name-quality-modifiers
  {"Aged Brie"                                 brie-quality-modify,
   "Backstage passes to a TAFKAL80ETC concert" backstage-quality-modify,
   "Sulfuras, Hand of Ragnaros"                (fn [_ quality] quality)}
  )

(def sell-in-modifiers
  {"Sulfuras, Hand of Ragnaros" identity}
  )

(defn get-quality-modifier
  [name]
  (let [exact-name-modify (get exact-name-quality-modifiers name)]
    (cond
      (re-find #"Conjured" name) conjured-quality-modify
      exact-name-modify exact-name-modify
      :else generic-item-quality-modify)
    ))

(defn get-sell-in-modifier
  [name]
  (get sell-in-modifiers name dec)
  )

(defn update-item-quality
  [item]
  (let [sell-in (:sell-in item)
        quality-modifier (get-quality-modifier (:name item))
        quality-modifier-for-sell-in (partial quality-modifier sell-in)]
    (update item :quality quality-modifier-for-sell-in)))

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
        updated-data (update-quality data)
        ]
    (doall (map println data))
    (println)
    (doall (map println updated-data))
    )
  )


