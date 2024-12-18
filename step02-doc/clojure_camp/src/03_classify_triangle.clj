
(defn triangle?
  "To check if 3 sides make a triangle, 
 	you need to check that every side is less than 
 	or equal to the sum of the other two sides."
  [a b c]
  (and (< a (+ b c))
       (< b (+ a c))
       (< c (+ a b))))

(defn classify-triangle
  "given 3 numbers, returns whether a triangle 
 is equilateral (3 equal sides), 
 isosceles (2 equal sides), 
 scalene, or, not a triangle."
  [a b c]
  (cond
    (not (triangle? a b c )) :invalid
    (= a b c) :equilateral
    (or (= a b) (= a c) (= b a) (= b c)) :isosceles
  		:else :scalene))
(classify-triangle 3 4 5)
(classify-triangle 0 0 0)