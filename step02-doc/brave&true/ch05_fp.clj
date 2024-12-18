(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total)))))

(defn sum_recur
  ([vals]
   (sum_recur vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
		 ;; recur is the tail recursion, very useful when the data is big
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(sum [39 5 1])
(sum_recur [39 5 1])

; comp
;; comp (compose), for creating a new function from the composition of any number of functions
;; from right to left
((comp inc *) 2 3)
((comp str +) 2 3)
((comp  reverse str *) 2 3 4)

(def character
  {:name "Smoothie"
   :attribute {:intelligence 10
               :strength 4
               :dexterity 5}})
;; do the same thing but differently
; comp
(def c-int (comp :intelligence :attribute))
(c-int character)
; expanding the comp into anonymous function
((fn [c] (:intelligence (:attribute c))) character)
(:intelligence (:attribute character))
; with get-in
(get-in character [:attribute :intelligence])

(def c-str (comp :strength :attribute))
((fn [c] (:strength (:attribute c))) character)
(c-str character)

(def c-name (comp :name))
(c-name character)
(get character :name)

(defn spell-slot [char]
  (int (inc (/ (c-int char) 2))))
(spell-slot character)
;; using comp, notice we go right (inner) to left
(def spell-slot-comp (comp int inc #(/ % 2) c-int))
(spell-slot-comp character)

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))
((two-comp str +) 1 2 3)

;; memoize (caching?)
(defn sleepy-therapy
  [x]
 	(Thread/sleep 1000)
 	(str x " slept"))
(sleepy-therapy "Mr. Freezeo")
;; memoize, only the first call waits one second; 
;; every subsequent function call returns immediately
(def memoize-sleep  (memoize sleepy-therapy))
(memoize-sleep "Captain Memoizer!")
;; This implementation could be useful for functions 
;; that are computationally intensive or that make network requests.

;; Peg
()

