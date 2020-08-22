(ns gilded-rose-clojure.helpers)

(defn is-in-inclusive-range
  [min max val]
  (contains? (set (range min (inc max))) val)
  )

(defn apply-n-times
  [f n val]
  (nth (iterate f val) n)
  )

(defn dec-keep-positive
  [val]
  (let [dec-val (dec val)]
    (if (> dec-val 0) dec-val 0)
    ))

(defn inc-keep-below
  [max val]
  (let [inc-val (inc val)]
    (if (> inc-val max) max inc-val)
    ))

(defn apply-sell-in-based-quality-modifier
  [modifier sell-in quality]
  (if (> sell-in 0)
    (apply-n-times modifier 1 quality)
    (apply-n-times modifier 2 quality)
    )
  )