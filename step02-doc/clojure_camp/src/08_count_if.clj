(ns count-if
  (:require
   [clojure.test :refer [is]]))

(defn count-if [pred? coll]
 (count (filter pred? coll)))

(defn count-if-tm [pred? coll]
  (->> coll
      	(filter pred?)
   				count))
(count-if even? [1 2 3 4 5 6])
(count-if (fn [x]
            (= x (reverse x)))
          [[1 2 1] [1 2 3 4] [1]])

(count-if-tm even? [1 2 3 4 5 6])


(is (= 3 (count-if even? [1 2 3 4 5 6])))

(is (= 1 (count-if keyword? ["foo" :bar (quote baz)])))

(is (= 2
       (count-if-tm (fn [x]
                   (= x (reverse x)))
                 [[1 2 1] [1 2 3 4] [1]])))

(clojure.test/run-tests)