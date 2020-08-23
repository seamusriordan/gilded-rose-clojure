(ns gilded-rose-clojure.standard-item
  (:require [gilded-rose-clojure.helpers :refer :all]))

(defn quality-modifier
  [sell-in quality]
  (apply-standard-item-modifier dec-keep-positive sell-in quality)
  )

(defn sell-in-modifier
  [sell-in]
  (dec sell-in)
  )