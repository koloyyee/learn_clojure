(ns find-largest)

;; Write a function largest to find the largest number in a list, 
;; making use of max and either apply or reduce.
;; Note that max takes a variable number of arguments, 
;; but does not work with arrays, 
;; ie. (max 1 2 3) works as expected, 
;; but (max [1 2 3]) does not.

(defn largest [nums]
  (apply  max nums))
(largest [1 2 -1 3 3 2 1 0])

(defn largest-reduce [nums]
  (reduce max nums))
(largest-reduce [1 2 -1 3 3 2 1 0])