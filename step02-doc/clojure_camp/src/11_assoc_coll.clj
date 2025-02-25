(ns assoc-coll
  (:require
   [clojure.test :refer [is]]))

(comment
  "Write a function get-and-set that makes a change to a collection at some location, 
  and return the original value at that location and the changed collection.
  get-and-set should take 3 arguments: a key, a value, and a collection (a vector or map).
  It should return a vector of two things: (1) the value that was at that key 
  in the original collection, and (2) the updated collection.")

(defn get-and-set [k v col] 
  ( let [ original (get  col k )]
  	[original (assoc  col k v ) ]
		))

["(defn get-and-set [k v col])"]

(is (= [1 [2 3 4]] (get-and-set 0 2 [1 3 4])))

(is (= ["foo"
        {:b "quux"
         :a "baz"}]
       (get-and-set :a
                    "baz"
                    {:b "quux"
                     :a "foo"})))

(clojure.test/run-tests)