;;; Copyright © 2017 Reb Cabin
;;; Distributed under The MIT License (MIT)

(ns funcyard.core)

(defn- inc-from-nil [v]
  (if (nil? v), 1, (inc v)))

(defn tally [items]
  "Produces a map with keys from items and values that count the number of times
  each item appears in the input. Valid only for sequences."
  (reduce
   (fn [talmap item]
     (into talmap
           {item (inc-from-nil (talmap item))}))
   {} items))

