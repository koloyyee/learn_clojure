(ns alchemy)

(defmacro backwards [form] (reverse form))

(backwards (" backwards" " am" "I" str))

;; everything is a list, AST.
;; eval
(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))
(eval (list 'def 'lucky-number (concat addition-list [10])))
lucky-number

;; reader interact with the reader directly
(read-string "(+ 1 2)")
(read-string "#(+ 1 %)")

;; They’re designated by macro characters, like ' (the single quote), #, and @. 
;; They’re also completely different from the macros we’ll get to later.
(read-string "'(a b c)")
(read-string "@var")

;; how it evaluate
(read-string "+")
; => +
(type (read-string "+"))
; => clojure.lang.Symbol
(list (read-string "+") 1 2)
; => (+ 1 2)
(eval (list (read-string "+") 1 2))
; => 3

;; basic of Macro
(read-string "(1 + 1)")
; => (1 + 1)
(eval (read-string "(1 + 1)"))
; => ClassCastException java.lang.Long cannot be cast to clojure.lang.IFn
(let [infix (read-string "(1 + 1)")]
  (list (second infix) (first infix) (last infix)))
; => (+ 1 1)
(eval
 (let [infix (read-string "(1 + 1)")]
   (list (second infix) (first infix) (last infix))))

;; Macro: Macros give you a convenient way to manipulate lists before Clojure evaluates them
(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))
(ignore-last-operand (+ 1 2 10))
;; the operands are not evaluated. In particular, symbols are not resolved; they are passed as symbols
;; Lists are not evaluated either
;; that is, the first element in the list is not called as a function, 
;; special form, or macro. 
;; Rather, the unevaluated list data structure is passed in.

;; this will fail
(defn ignore-last-operand
  [function-call]
  (butlast function-call))
(ignore-last-operand (+ 1 2 10))

;; We can look into it with
(macroexpand '(ignore-last-operand (+ 1 2 10)))
(macroexpand '(ignore-last-operand (+ 1 2 (println "look at me!!!"))))
(macroexpand '(->> c first (reduce +)))



(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))
(infix (1 * 2))

;; Exercises
;; These exercises focus on reading and evaluation. Chapter 8 has exercises for writing macros.
;; Use the list function, quoting, and read-string to create a list that, when evaluated, prints your first name and your favorite sci-fi movie.
(list (read-string  "println"))
(eval
 (let [func (list (read-string  "println"))]
   (list (first func) "David" "Star Wars")))
(defmacro my-name-sci-fi
  [name movie]
  (list (read-string "println") name " likes" movie))
(my-name-sci-fi "David" "Star Wars")
;; Create an infix function that takes a list like (1 + 3 * 4 - 5) and transforms it into the lists that Clojure needs in order to correctly evaluate the expression using operator precedence rules.
