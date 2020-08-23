(ns gilded-rose-clojure.brie
  (:require [gilded-rose-clojure.helpers :refer :all]))

(defn quality-modifier
  [sell-in quality]
  (let [inc-keep-below-50 (partial inc-keep-below 50)]
    (apply-standard-item-modifier inc-keep-below-50
                                  sell-in quality)
    ))