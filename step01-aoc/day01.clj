(ns day01
  (:require
   [read-file :as rf ]))


;; part 1
(defn filter-digit-only [line]
  (filter #(> % 0)
          (mapv #(Character/digit % 10)
                line)))
(defn add-first-last [digits]
  (+ (* ( first digits ) 10) ( last digits)))

(let [lines (rf/read-lines "day01.txt")]
(reduce  + (mapv (comp add-first-last filter-digit-only) lines)))


