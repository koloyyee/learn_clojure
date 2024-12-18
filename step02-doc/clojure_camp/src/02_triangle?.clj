(defn triangle?
  "To check if 3 sides make a triangle, 
 	you need to check that every side is less than 
 	or equal to the sum of the other two sides."
  [a b c]
  (and (<= a (+ b c))
       (<= b (+ a c))
       (<= c (+ a b))))
(triangle? 3 1 1 )