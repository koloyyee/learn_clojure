(ns scrabble-score)

;; Implement a function score that calculates the scrabble score for a given word.
;; Use google to find out how much each letter is worth.


;; 0 Points - Blank tile.
;; 1 Point - A, E, I, L, N, O, R, S, T and U.
;; 2 Points - D and G.
;; 3 Points - B, C, M and P.
;; 4 Points - F, H, V, W and Y.
;; 5 Points - K.
;; 8 Points - J and X.
;; 10 Points - Q and Z.

(= "hello" 8)
;; h - 4 * 1
;; e, l, l, o - 1 * 4 
;; = 8

(= "question" 17)
;; q - 10 * 1
;; u, e, s, t, i, o, n - 1 * 7
;; 17

(= "quizzed")
;; q, z, z - 10 * 3 
;; u, i, e - 1 * 3
;; d - 2 * 1
;; 35
(def points
  {\a 1 \e 1 \i 1 \l 1 \n 1 \o 1 \r 1 \s 1 \t 1 \u 1
   \d 2 \g 2
   \b 3 \c 3 \m 3 \p 3
   \f 4 \h 4 \v 4 \w 4 \y 4
   \k 5
   \j 8 \x 8
   \q 10 \z 10})
;; (defn score [str] 
;;   (apply + (map #()) str))
;; (score points)
(defn score [str]
  (apply + (map #(points %) str)))

(defn score2 [str]
  (->> str
       (map #(points %))
       (apply +)))

(score2 "hello")

(def m
  [{:id 99
    :name "David"
    :terms 3/4
    :subject :cp}
   {:id 19
    :name "George"
    :terms 2/4
    :subject :cp}
   {:id 15
    :name "Paul"
    :terms 4/4
    :subject :art}])

(defn normal [m]
  (reverse (sort-by :id (filter #(= (:subject %) :cp) m))))

(normal m)

(defn thread-last [m]
  (->> m
      	(filter #(= (:subject %) :cp))
				(sort-by :id)
				reverse
   				))
(thread-last m )
(= (normal m) (thread-last m ))