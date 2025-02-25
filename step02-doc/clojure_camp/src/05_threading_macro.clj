(ns threading-macro)

;; From
(defn process-list
  [coll]
  (map (fn [x] (* x 10))
       (filter even? (map inc coll))))

(def pl (process-list [1 2 3 4 5 6]))
;; To
(defn process-list-tm [coll]
  (->> coll
       (map inc)
       (filter even?)
       (map #(* % 10) )))

;; (defn process-list-tm
;;   [coll]
;;   (->> (map inc coll)
;;        (filter even?)
;;        (map (fn [x] (* x 10)))));

(def pltm (process-list-tm [1 2 3 4 5 6]))
(= pl pltm)

;; From 
(defn process-number [n] (* (+ (inc (/ n 1.5)) 2) 10))
(def pn (process-number 2))
;; To 
(defn process-num-tm [n]
  (-> n
      (/ 1.5)
      inc
      (+ 2 )
      (* 10)))

(def pntm (process-num-tm 2 ))
(= pn pntm)
pn
pntm

(-> "hello world"
    .toUpperCase
    (.replace "HELLO" "HI")
    ;; (.substring 0 3)
  		)
(->> 3
     ( * 3) 
  		 ( / 2 )
 			 (- 4 )
 			 )
