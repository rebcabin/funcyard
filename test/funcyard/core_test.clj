;;; Copyright © 2017 Reb Cabin
;;; Distributed under The MIT License (MIT)

(ns funcyard.core-test
  (:require [clojure.test       :refer :all]
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
