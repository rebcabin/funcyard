;;; Copyright © 2017 Reb Cabin
;;; Distributed under The MIT License (MIT)

(ns funcyard.core
  (:require clojure.pprint
            clojure.string))

(defn- inc-from-nil [v]
  (if (nil? v), 1, (inc v)))

(defn tally [items]
  "Produces a map with keys from items and values that count the number of times
  each item appears in the input collection."
  (reduce
   (fn [talmap item]
     (into talmap
           {item (inc-from-nil (talmap item))}))
   {} items))

(defn pairwise [binary-function items]
  "Zips the binary function down the a copy of the items with the first element
  removed and the items. Useful for differences, piecewise geospatial distances,
  numerical derivatives, and the like. \"Items\" must contain at least two
  elements."
  (when (and (first items) (second items))
    (map binary-function (drop 1 items) items)))

(defn transpose
  "Produces the transpose of the given two-dimensional collection of
  collections. A matrix is any collection on which \"get-in\" with a
  two-argument sequence of keys would work."
  [matrix]
  (apply map list matrix))

;;               _    _           _   _
;;  __ ___ _ __ | |__(_)_ _  __ _| |_(_)___ _ _  ___
;; / _/ _ \ '  \| '_ \ | ' \/ _` |  _| / _ \ ' \(_-<
;; \__\___/_|_|_|_.__/_|_||_\__,_|\__|_\___/_||_/__/

(defn combinations [xs m]
  (cond
    (= m 0) (list ())
    (empty? (seq xs)) ()
    :else (let [x (first xs)
                xs (rest xs)]
            (concat
             (map #(cons x %) (combinations xs (- m 1)))
             (combinations xs m)))))

;;                          _        _   _
;;  _ __  ___ _ _ _ __ _  _| |_ __ _| |_(_)___ _ _  ___
;; | '_ \/ -_) '_| '  \ || |  _/ _` |  _| / _ \ ' \(_-<
;; | .__/\___|_| |_|_|_\_,_|\__\__,_|\__|_\___/_||_/__/
;; |_|

(defn permutations [l]
  (let [c (count l)]
   (if (or (= c 0) (= c 1)),
     (seq [l]),
     (let [splits (map #(split-at % l) (range c))
           pivots (map (comp first second) splits)
           pres   (map first splits)
           posts  (map (comp rest second) splits)
           resids (map concat pres posts)
           subps  (map permutations resids)
           reasm  (mapcat #(map (partial cons %1) %2) pivots subps)]
       reasm
       ))))

(defn string-permutations [s]
  (map clojure.string/join (permutations s)))

;;                       _
;;  _ __  __ _ _ __ _ __(_)_ _  __ _
;; | '  \/ _` | '_ \ '_ \ | ' \/ _` |
;; |_|_|_\__,_| .__/ .__/_|_||_\__, |
;;            |_|  |_|         |___/

(defn map-down-one [fun colls]
  "Map the given function one level down in the collection of collections."
  (map #(map fun %) colls))

;;                 _ _           _ _ _   _
;;  __ __ _ _ _ __| (_)_ _  __ _| (_) |_(_)___ ___
;; / _/ _` | '_/ _` | | ' \/ _` | | |  _| / -_|_-<
;; \__\__,_|_| \__,_|_|_||_\__,_|_|_|\__|_\___/__/

(defn has-duplicates [coll]
  (and (seq coll) ;; idiom for (not (empty? ...))
       ;; https://clojuredocs.org/clojure.core/empty_q
       ;; Could use "not-empty."
       ;; https://clojuredocs.org/clojure.core/not-empty
       (not (= (count (into #{} coll))
               (count coll)))))
