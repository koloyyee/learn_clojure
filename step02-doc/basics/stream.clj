
(def numbers [1 2 3 4 5 6 7 8 9 10])
;; this 
(map #(* % %) (take-while even? numbers))
;; or this.
(->> numbers
     (filter even?)
     (map
      #(* % %)))

