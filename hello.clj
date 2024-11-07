(ns hello
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as str]
            [java-time.api :as t]))

(defn time-str
  "Returns a string representation of a datetime in the local time zone."
  [instant]
  (t/format
   (t/with-zone (t/formatter "hh:mm a") (t/zone-id))
   instant))

(defn run [opts]
  (println  opts + "Hello world, the time is" (time-str (t/instant))))

(defn square [x] (* x x))
(run  4)

(println "hello")
(def x "this is x")
(print (str/upper-case x))
(print (square 9))
(def nums [99 129 12 115])

(defn find-gcd [nums]
  (math/gcd (apply min nums)
            (apply max nums)))
(find-gcd nums)

(conj nums 1)

(def lnums (list 999 129 12 115))
(conj lnums 1)
(defn print-list [lst]
  (when (seq lst)
    (println (first lst))
    (recur (rest lst))))

(print-list lnums)

(+ (first (seq lnums)) (apply + (rest (seq lnums))))

(+ 1 2)
(def l '(1 2 3))
(def new-l (conj l 4))
(conj l 4)

(def v [1 2 3])
(def vv (conj v 10))

(println vv)

(def m {:a 3, :b 3, :c {:d  1}})
(get-in  m [:c  :d])
(def matrix [[1 2 3]
             [4 5 6]
             [7 8 9]])
(println matrix)
(get-in matrix [0 0])

(def game-history matrix)


