;;; Copyright © 2017 Reb Cabin
;;; Distributed under The MIT License (MIT)

(ns funcyard.core-test
  (:require
   clojure.pprint
   [clojure.test       :refer :all]
   [flatland.useful.fn :as    flatland]
   [funcyard.core      :refer :all]))

;;  _        _ _
;; | |_ __ _| | |_  _
;; |  _/ _` | | | || |
;;  \__\__,_|_|_|\_, |
;;               |__/

(deftest a-test-of-tally
  (testing "tally on vectors"
    (is (= {'a, 2, 'b 1, 'c 2}
           (tally ['c 'a 'b 'a 'c])))))

(deftest tally-on-empty-vector
  (testing "tally on empty vector"
    (is (= {}
           (tally [])))))

(deftest tally-on-empty-list
  (testing "tally on empty list"
    (is (= {}
           (tally ())))))

(deftest tally-on-empty-set
  (testing "tally on empty set"
    (is (= {}
           (tally #{})))))

;;      _  __  __ _
;;  _ _(_)/ _|/ _| |___
;; | '_| |  _|  _| / -_)
;; |_| |_|_| |_| |_\___|

(deftest riffle-test
  (testing "riffle")
  (is (not (seq (riffle () '(1 2 3)))))
  (is (= '((a 1) (b 2) (c 3))
         (riffle '(a b c) '(1 2 3))))
  (is (= '((a 1) (b 2) (c 3))
         (riffle '(a b c) '(1 2 3) vector)))
  (is (= '(#{a 1} #{b 2} #{c 3})
         (riffle '(a b c) '(1 2 3) (comp set list))))
  (is (= '(#{a 1} #{b 2} #{c 3})
         (riffle '(a b c) '(1 2 3) (comp set vector)))))

(deftest rifflecat-test
  (testing "rifflecat")
  (is (not (seq (rifflecat () '(1 2 3)))))
  (is (= '(a 1 b 2 c 3)
         (rifflecat '(a b c) '(1 2 3))))
  (is (= '(a 1 b 2 c 3)
         (rifflecat '(a b c) '(1 2 3) vector)))
  (is (= '(1 a, 2 b, 3 c)
         (rifflecat '(a b c) '(1 2 3) (comp set list))))
  (is (= '(1 a, 2 b, 3 c)
         (rifflecat '(a b c) '(1 2 3) (comp set vector)))))

;;             _            _
;;  _ __  __ _(_)_ ___ __ _(_)___ ___
;; | '_ \/ _` | | '_\ V  V / (_-</ -_)
;; | .__/\__,_|_|_|  \_/\_/|_/__/\___|
;; |_|

(deftest pairwise-on-squares
  (testing "pairwise on list of squares"
    (is (= [5 7 9]
           (pairwise - [4 9 16 25])))))

(deftest pairwise-on-short-things
  (testing "pairwise should produce nil if there are fewer than 2 items."
    (is (nil? (pairwise - [42])))
    (is (nil? (pairwise - [0])))
    (is (nil? (pairwise - [])))
    (is (nil? (pairwise - '(42))))
    (is (nil? (pairwise - '())))
    (is (nil? (pairwise - #{42})))
    (is (nil? (pairwise - #{})))))

(deftest pairwise-expect-divide-by-zero
  (testing "pairwise divides in the correct direction."
    (is (thrown? java.lang.ArithmeticException
                 (doall (pairwise / [0 1]))))))

;;  _
;; | |_ _ _ __ _ _ _  ____ __  ___ ___ ___
;; |  _| '_/ _` | ' \(_-< '_ \/ _ (_-</ -_)
;;  \__|_| \__,_|_||_/__/ .__/\___/__/\___|
;;                      |_|

(deftest transpose-vector-of-vectors
  (testing "transpose on vector of vectors"
    (is (= [[1 3] [2 4]]
           (transpose [[1 2] [3 4]])))))

(deftest transpose-of-singleton
  (testing "transpose of a 1-by-1 matrix"
    (is (= '((1))
           (transpose [[1]])))))

(deftest transpose-of-empty
  (testing "transpose of an empty structure"
    (is (= '()
           (transpose [[]])))))

;;               _    _           _           _
;;  __ ___ _ __ | |__(_)_ _  __ _| |_ ___ _ _(_)__ ___
;; / _/ _ \ '  \| '_ \ | ' \/ _` |  _/ _ \ '_| / _(_-<
;; \__\___/_|_|_|_.__/_|_||_\__,_|\__\___/_| |_\__/__/

(deftest combinations-basic
  (testing "combinations"
    (is (= '((a b) (a c) (a d) (b c) (b d) (c d))
           (combinations '(a b c d) 2))
        (= (partition 1 '(a b))
           (combinations '(a b) 1)))))

(deftest permutations-basic
  (testing "basics of permutations"
    (is (= (permutations ['a 'b 'c 'd])
           '((a b c d) (a b d c) (a c b d) (a c d b)
             (a d b c) (a d c b) (b a c d) (b a d c)
             (b c a d) (b c d a) (b d a c) (b d c a)
             (c a b d) (c a d b) (c b a d) (c b d a)
             (c d a b) (c d b a) (d a b c) (d a c b)
             (d b a c) (d b c a) (d c a b) (d c b a))))))

(deftest testing-permutation-singleton
  (is (= '((a))
         (permutations '(a)))))

(deftest transpose-of-permutations
  (is (= '((a a b b c c)
           (b c a c a b)
           (c b c a b a))
         (transpose (permutations '(a b c))))))

(deftest string-permutations-basic
  (is (= (string-permutations "meat")
         '("meat" "meta" "maet" "mate" "mtea" "mtae"
           "emat" "emta" "eamt" "eatm" "etma" "etam"
           "amet" "amte" "aemt" "aetm" "atme" "atem"
           "tmea" "tmae" "tema" "team" "tame" "taem"))))

;; TODO: This is an ideal place to introduce property-based testing.
(deftest cycle-right-test
  (is (= () (cycle-right ())))
  (is (= '(1) (cycle-right '(1))))
  (is (= '(2 1) (cycle-right '(1 2))))
  (is (= '(3 1 2) (cycle-right '(1 2 3))))
  (is (= [] (cycle-right [])))
  (is (= [1] (cycle-right [1])))
  (is (= [2 1] (cycle-right [1 2])))
  (is (= [3 1 2] (cycle-right [1 2 3])))
  (is (= () (cycle-right 1 ())))
  (is (= '(1) (cycle-right 1 '(1))))
  (is (= '(2 1) (cycle-right 1 '(1 2))))
  (is (= '(3 1 2) (cycle-right 1 '(1 2 3))))
  (is (= [] (cycle-right 1 [])))
  (is (= [1] (cycle-right 1 [1])))
  (is (= [2 1] (cycle-right 1 [1 2])))
  (is (= [3 1 2] (cycle-right 1 [1 2 3])))
  (is (= () (cycle-right 2 ())))
  (is (= '(1) (cycle-right 2 '(1))))
  (is (= '(1 2) (cycle-right 2 '(1 2))))
  (is (= '(2 3 1) (cycle-right 2 '(1 2 3))))
  (is (= [] (cycle-right 2 [])))
  (is (= [1] (cycle-right 2 [1])))
  (is (= [1 2] (cycle-right 2 [1 2])))
  (is (= [2 3 1] (cycle-right 2 [1 2 3])))  )

(deftest cycle-left-test
  (is (= () (cycle-left ())))
  (is (= '(1) (cycle-left '(1))))
  (is (= '(2 1) (cycle-left '(1 2))))
  (is (= '(2 3 1) (cycle-left '(1 2 3))))
  (is (= [] (cycle-left [])))
  (is (= [1] (cycle-left [1])))
  (is (= [2 1] (cycle-left [1 2])))
  (is (= [2 3 1] (cycle-left [1 2 3])))
  (is (= () (cycle-left 1 ())))
  (is (= '(1) (cycle-left 1 '(1))))
  (is (= '(2 1) (cycle-left 1 '(1 2))))
  (is (= '(2 3 1) (cycle-left 1 '(1 2 3))))
  (is (= [] (cycle-left 1 [])))
  (is (= [1] (cycle-left 1 [1])))
  (is (= [2 1] (cycle-left 1 [1 2])))
  (is (= [2 3 1] (cycle-left 1 [1 2 3])))
  (is (= () (cycle-left 2 ())))
  (is (= '(1) (cycle-left 2 '(1))))
  (is (= '(1 2) (cycle-left 2 '(1 2))))
  (is (= '(3 1 2) (cycle-left 2 '(1 2 3))))
  (is (= [] (cycle-left 2 [])))
  (is (= [1] (cycle-left 2 [1])))
  (is (= [1 2] (cycle-left 2 [1 2])))
  (is (= [3 1 2] (cycle-left 2 [1 2 3])))  )

;; Thanks to A. Webb: https://goo.gl/dAlROn

;; Dot products with 720, 120, 24, 6, 2, 1, 1

(deftest testing-factoradic
  (is (=       '(1 2 0 0) (factoradic 10)))
  (is (=     '(4 0 2 0 0) (factoradic 100)))
  (is (= '(1 2 1 2 2 0 0) (factoradic 1000))))

(deftest testing-nth-permutation
  (is (= (permutations '(a b c))
         (map #(nth-permutation '(a b c) %) (range 6))))
  (is (= (permutations '(a b))
         (map #(nth-permutation '(a b) %) (range 2))))
  (is (= (permutations '(a))
         (map #(nth-permutation '(a) %) (range 1))))
  ;; This next one is not true because nth-permutation has subtle behavior on
  ;; empty input.
  #_(is (= (permutations '())
         (map #(nth-permutation '() %) (range 1)))))

;;                       _
;;  _ __  __ _ _ __ _ __(_)_ _  __ _
;; | '  \/ _` | '_ \ '_ \ | ' \/ _` |
;; |_|_|_\__,_| .__/ .__/_|_||_\__, |
;;            |_|  |_|         |___/

(def colls [['a 'b 'c] ['a 'a 'b] ['a 'c 'b 'd]])

(def colls-2 [[{:suit :H} {:suit :C}] [{:suit :S} {:suit :D}]])

(def colls-3 (flatten colls-2))

(deftest map-down-one-test
  (is (= colls (map-down-one identity colls)))
  (is (= [[:H :C] [:S :D]]
         (map-down-one :suit colls-2)))
  (is (not-any? #(apply = %)
                (map-down-one :suit colls-2)))
  (is (= '(()) (map-down-one identity '(())))))

;;                 _ _           _ _ _   _
;;  __ __ _ _ _ __| (_)_ _  __ _| (_) |_(_)___ ___
;; / _/ _` | '_/ _` | | ' \/ _` | | |  _| / -_|_-<
;; \__\__,_|_| \__,_|_|_||_\__,_|_|_|\__|_\___/__/

(deftest has-duplicates-test
  (is (not (has-duplicates #{})))
  (is (not (has-duplicates {})))
  (is (not (has-duplicates '())))
  (is (not (has-duplicates [])))
  (is (not (has-duplicates colls-3)))
  (is (has-duplicates (conj (map :suit colls-3) :S))))
