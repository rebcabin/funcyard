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

;;               _    _           _           _
;;  __ ___ _ __ | |__(_)_ _  __ _| |_ ___ _ _(_)__ ___
;; / _/ _ \ '  \| '_ \ | ' \/ _` |  _/ _ \ '_| / _(_-<
;; \__\___/_|_|_|_.__/_|_||_\__,_|\__\___/_| |_\__/__/

(defn combinations [xs m]
  (cond
    (= m 0) (list ())
    (empty? (seq xs)) ()
    :else (let [x (first xs)
                xs (rest xs)]
            (concat
             (map #(cons x %) (combinations xs (- m 1)))
             (combinations xs m)))))

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

(defn cycle-left
  ([n a-seq] {:pre [(>= n 0)]}
   (reduce (fn [s _] (cycle-left s)) a-seq (range n)))
  ([a-seq]
   (if (seq a-seq)
     (conj (vec (rest a-seq)) (first a-seq))
     a-seq)))

(defn cycle-right
  ([n a-seq] {:pre [(>= n 0)]}
   (reduce (fn [s _] (cycle-right s)) a-seq (range n)))
  ([a-seq]
   (if (seq a-seq)
     (cons (last a-seq) (butlast a-seq))
     a-seq)))

;; Thanks to A. Webb for the following: https://goo.gl/dAlROn

(def factorials (reductions * 1 (drop 1 (range))))

(defn factoradic [n] {:pre [(>= n 0)]}
  (loop [a (list 0), n n, p 2]
    (if (zero? n) a (recur (conj a (mod n p))
                           (quot n p)
                           (inc p)))))

(defn nth-permutation [s n] {:pre [(< n (nth factorials (count s)))]}
  (let [d (factoradic n)
        choices (concat (repeat (- (count s) (count d))
                                0)
                        d)]
    ((reduce
      (fn [m i]
        (let [[left [item & right]]
              (split-at i (m :rem))]
          (assoc m
                 :rem (concat left right)
                 :acc (conj (m :acc) item))))
      {:rem s :acc []}
      choices)
     :acc)))

;;                       _
;;  _ __  __ _ _ __ _ __(_)_ _  __ _
;; | '  \/ _` | '_ \ '_ \ | ' \/ _` |
;; |_|_|_\__,_| .__/ .__/_|_||_\__, |
;;            |_|  |_|         |___/

(defn map-down-one [fun colls]
  "Map the given function one level down in the collection of collections."
  (map #(map fun %) colls))

;;; TODO: (map-down fun colls level-spec), like Mathematica MapAt

;;      _  __  __ _
;;  _ _(_)/ _|/ _| |___
;; | '_| |  _|  _| / -_)
;; |_| |_|_| |_| |_\___|

(defn riffle
  ([coll1 coll2] (riffle coll1 coll2 list))
  ([coll1 coll2 combiner]
   (map combiner coll1 coll2)))

;;; TODO: ternary and more.

(defn rifflecat
  ([coll1 coll2]
   (apply concat (riffle coll1 coll2)))
  ([coll1 coll2 combiner]
   (apply concat (riffle coll1 coll2 combiner))))

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

