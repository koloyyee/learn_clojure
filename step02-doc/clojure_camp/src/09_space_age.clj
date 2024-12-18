(ns space-age
  (:require
   [clojure.test :refer [is]]))

;; Implement a function that lets someone convert their age on one planet to their age on another.
;; For example:
;; 30 years on Earth is about 1 year on Saturn (a year being a single revolution around the sun).
;; 10 years on Mars is 78 years on Mercury.
;; You will need to look up the relevant data on planets yourself.

;; * int /
(is (= 1 1))



(defn days-to-earth-year [days]
  "
Mercury: 88 days
Venus: 225 days
Earth: 365 days
Mars: 687 days
Jupiter: 4,333 days
Saturn: 10,759 days
Uranus: 30,687 days
Neptune: 60,190 days
"
  (double (/ days  365)))
(days-to-earth-year 365)
(def days-map {:mercury 88
               :venus 225
               :earth 365
               :mars 687
               :jupiter 4333
               :saturn 10759
               :uranus 30687
               :neptune 60190})

(defn days-to-years-map [days-map]
  (into {}
        (map (fn [[k v]]
               [k (days-to-earth-year v)])
             days-map)))

(def plant-convert (days-to-years-map days-map))
(plant-convert :mars)

(defn convert-space-age [age source-planet target-planet]
  (int (* age 
	(/ (plant-convert source-planet) (plant-convert  target-planet)))))


(is (= 1 (convert-space-age 30 :earth :saturn)))

(is (= 78 (convert-space-age 10 :mars :mercury)))
(convert-space-age 10 :mars :mercury)