(ns gilded-rose-clojure.backstage
  (:require [gilded-rose-clojure.helpers :refer :all]))

(defn quality-modifier
  [sell-in quality]
  (
    let [max-quality 50
         inc-keep-below-50 (partial inc-keep-below max-quality)
         inc-quality-n-times #(apply-n-times inc-keep-below-50 % quality)]
    (cond
      (> sell-in 10) (inc-quality-n-times 1)
      (is-in-inclusive-range 6 10 sell-in) (inc-quality-n-times 2)
      (is-in-inclusive-range 1 5 sell-in) (inc-quality-n-times 3)
      :else 0)
    ))