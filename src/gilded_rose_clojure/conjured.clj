(ns gilded-rose-clojure.conjured
  (:require [gilded-rose-clojure.helpers :refer :all])
  )

(defn quality-modifier
  [sell-in quality]
  (let [dec-twice (partial apply-n-times dec-keep-positive 2)]
    (apply-standard-item-modifier dec-twice
                                  sell-in quality)
    ))