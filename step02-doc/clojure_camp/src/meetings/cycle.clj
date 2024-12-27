(ns meetings.cycle)

;; 19th dec 2024
;;

;; cycle
;; a -> b
;; b -> c
;; c -> d
;; d -> a 
;;

;; cycle
;; e -> f 
;; f -> e 
;;
(def input "fe ab cd ef da")
(= #{1 2} #{2 1})
