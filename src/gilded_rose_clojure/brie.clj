(ns gilded-rose-clojure.brie
  (:require [gilded-rose-clojure.helpers :refer :all]))

(defn quality-modify
  [sell-in quality]
  (let [inc-keep-below-50 (partial inc-keep-below 50)]
    (apply-sell-in-based-quality-modifier inc-keep-below-50 sell-in quality)
    )
  )