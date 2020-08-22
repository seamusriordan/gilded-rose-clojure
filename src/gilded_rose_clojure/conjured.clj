(ns gilded-rose-clojure.conjured
  (:require [gilded-rose-clojure.helpers :refer :all])
  )

(defn quality-modify
  [sell-in quality]
  (let [dec-twice (comp dec-keep-positive dec-keep-positive)]
    (apply-sell-in-based-quality-modifier dec-twice sell-in quality))
  )