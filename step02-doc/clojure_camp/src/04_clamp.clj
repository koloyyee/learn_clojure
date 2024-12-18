(defn clamp
  "restrict a value to a given range"
  [x min max]
  (cond
    (< x min) min
    (<= x max) x
    :else max))
(clamp 2 1 4)
(clamp 0 1 4)
(clamp 5 1 4)

(defn ans [x min max]
  (cond (<= min x max) x ;; see if it is in non-decreasing order
        (< x min) min
        :else max))
(ans 2 1 4)
(ans 0 1 4)
(ans 5 1 4)