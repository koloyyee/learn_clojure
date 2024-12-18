;; From
(defn process-value
  [value]
  (if (and (number? value) (> value 10))
    :pretty-big
    (if (and (number? value) (< value 0))
      :negative
      (if (and (number? value) (zero? value))
        :zero
        (if (number? value)
          :small-number
          (if (string? value)
            :a-string
            :something-else))))))
(process-value -1)
(process-value 1)
(process-value 11)

;; To
(defn process-val [value]
  (if (number? value)
    (cond (> value 10) :pretty-big
          (< value 0) :negative
          (zero? value) :zero
          (string? value) :a-string
          :else :small-number)
    :something-else))
(process-val -1)
(process-val 1)
(process-val 11)
(process-val "abc")

(defn ans [value]
  (cond (string? value) :a-string
        (not (number? value)) :something-else
        (zero? value) :zero
        (>  value 10) :pretty-big
        (< value 0) :negative
        :else :small-number))
(ans -1)
(ans 1 )
(ans 11 )
(ans "11" )
(ans {:a 1} )