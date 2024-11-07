(ns ch03-do-things
  (:require [clojure.string]))

;; String
(str "hello")
(str "hello" "orld" "!")

;; Basic Operation 
(+ 1 2)
(- 2 1)
(* 2 3)
(/ 6 4)

(conj '(1 2 3 4) 5)
(conj [1 2 3 4] 5)
;; Conditional 
(if true "Bolt!"  "Hammer!")
(if false "zeus" "thor")
(if true  "zeus")
  ;; neg will be nil
(if false  "zeus")
;; The do operator lets you wrap up multiple forms in parentheses and run each of them.
;; like do this and this 
(if false
  (do (println "Success")
      "Zeus")
  (do (println "Failed")
      "Thor"))
(if (> 4 2)
  (do (println (* 2 2))
      "2 * 2")
  (do (println  (/ 4 2))
      "2 / 2"))
;; The when operator is like a combination of if and do, but with no else branch
;; do this and this 
(when  true  (println "No way this will run")  "Wrong, but also right")
(when (< 1 3) (println (+ 1 3) (+ 5 -1)))

;; question mark for conditional
;; Clojure has true and false values. nil is used to indicate no value in Clojure.
(nil? nil)
(nil? 1)
(odd? (- 3 6))
(even? (- 3 6))

;; Both nil and false are used to represent logical falsiness, 
;; whereas all other values are logically truthy. 
(if nil "neal" "leo") ;; nil is falsey therefore is "leo"
;; Truthy and falsey refer to how a value is treated in a Boolean expression, 
;; like the first expression passed to if
(if "bear" "salmon" "fish") ;; the "bear" is string therefore is truethy
(if (odd? (- 1 2)) "odd"  "even")
(if "bear eat beets" "bears beets Battlestar Galactica")
(if nil "This won't be the result because nil is falsey" "nil is falsey")

;; Equality
(= 1 1)
(= "a" "a")
(= nil nil)
(= 1  2)
(= 1.1 2.1)

;; or returns either the first truthy value or the last value
(or false nil :large :small) ;; nil is false so it will go the next one 
(or false nil false :small) ;; nil is false so it will go the next one 
(or (= 1 2) (= "yes" "no"))
(or nil)

;; and returns the first falsey value or, if no values are falsey
(and :free_wifi :hot_coffee) ;; hot_coffee is the last truthy value
(and :free_wifi nil :not_here) ;; nil is the first falsey value

;; Declaration
;; def: define variables
(def failed-protagonist-names
  ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])
failed-protagonist-names
(def severity :mild)
(def error-message "Oh god!")
(if (= severity :mild)
  (def error-message (str error-message "Mildly Inconvenienced"))
  (def error-message (str error-message "Doomed!")))

;; defn: define functions
(defn error-message
  [severity]
  (str "Oh God! It's a disaster! We're "
       (if (= severity :mild)
         "Mildly incoveniced!"
         "Dooooooooomed")))
(error-message "pork")
(error-message :mild)

;; anonymous function
((fn [x y] (* x y))  2 4)

(def message {:mild "that's not good", :spicy "what's the fuck is this?"})
(:spicy message)
(get message :mild)

;; Data Structure
;; Maps
(def person {:first-name "Charlie" :last-name "McFishwich"})
(def group  {:member {:person person}})
(:member group)
;; creating hash map verbose
(hash-map :a 1 :b 2)
;; get the surface one 
(get group :member)
(get {:a 0 :b 1} :c "unicorns?")
;; get nested value
(get-in group [:member :person :first-name])
({:name "The human coffeepot"} :name)
;; nil if not exists
({:name "The human coffeepot"} :age)

;; Keywords
(:a {:a 1 :b 2 :c 3})
;; access value with and w/o get 
({:a 1 :b 2 :c 3} :c)
(get {:a 1 :b 2 :c 3} :a)

;; default or fallback value. this is very useful just like nullish
(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")

;; Vectors
[3 2 1]
(get [3 2 1] 1)
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
(get-in ["a" {:name "Pugsley Winterbottom"} "c"] [1 :name])

;; verbose
[vector "creepy" "full" " moon"]
;; conj: "push" to the back like queue
(conj [1 2 3] 4)

;; List
;; difference between List and Vector
;; no "get" in List, access with "nth" and index
'(1 2 3 4)
(nth '(1 2 3 4) 0)
(nth '(:a :b :c) 2)
(nth '({:a 1 :b 2} {:a 3 :b 4}) 1)
(get (nth '({:a 1 :b 2} {:a 3 :b 4}) 1) :a)
;; verbose
(list 1 "two" {3 4})
;; conj: "push" to front like stack
(conj '("a", "b", "c") "z")

;; IMPORTANT: when to use List vs Vector
;; Macro -> List
;; Normally -> Vector

;; Set
;; unordered unique collection.
#{"kurt vonnegut" 20 :icicle}
;; verbose
(hash-set 1 1 2 2)
;; #{1 1 2 2 } ;; error
(conj #{:a :b})
(set [3 3 3 4 5 6 6])
(set [{:a 1 :b 2} {:a 1 :b 2}])
(set [[1 2 3] [2 3 4] [1 2 3]])

;; how to check? contains (only works with set.)
(contains? #{:a :b} :a)
(contains? '["b" "c" "d"] "d") ;; list
(contains? ["b" "c" "d"] "d") ;; vector
(contains? #{"b" "c" "d"} "d")
(contains? #{nil} nil)
(contains? #{nil 3} 1)

;; shorthand
(:a #{:a :b})
;; find an element
(get #{1 ,2} 1)
(get #{{:a 1, :b 2} {:a 3, :b 4}} {:a 1 :b 2})
(= {:a 1, :b 2} {:a 1 :b 2})

;; Calling Functions
(first [1 2 3 4])
(last [1 2 3 4])
(peek [1 2 3 4])
(pop [1 2 3 4])
;; or return first truethy
((or + -) 1 2 3 4)
;; and returns first falsey or last truethy
((and (= 1 1) +) 1 2 3)
((and (= 1 1) + -) 1 2 3)

;; func with func
(map inc [1 2 3])
(map + [1 2 3] (iterate inc 2))
(+ (inc 199) (/ 100 (- 7 2))
   (+ 200 (/ 100 (- 7 2)))
   (+ 200 (/ 100 5))
   (+ 200 20)
   220)
;; the steps of evaluation
(+ (inc 199) (/ 100 (- 7 2))) ;; (inc 199)
(+ 200 (/ 100 (- 7 2))) ;; evaluate ( - 7 2 )
(+ 200 (/ 100 5)) ;; ( / 100 5 )
(+ 200 20) (+ 200 20)
220

;; defn
(defn too-enthusiastic  ;; define function name
  "Return a cheer that might be a bit too enthusiastic" ;; docstring
  [name] ;; parameter
  (str "Oh god! " name ", you are the best!"))
(too-enthusiastic "Paul")
;; parameters
(defn zero-param [] (str "nothing"))
(defn one-param [one] (str "exactly "  one " parameter"))
(defn four-params [a b c d] (count (str a b d c)))

(zero-param)
(one-param "one")
(one-param [1 2 2 3])
(four-params "f" "o" "u" "r")

;; multi-arity: function overloading
(defn multi-arity
;; 3-arity arguements and body
  ([first-arg second-arg third-arg]
   (* first-arg second-arg third-arg))
;; 2-arity argument and body
  ([first-arg second-arg]
   (+ first-arg second-arg))
;; 1-arity 
  ([first-arg] (str first-arg)))

(multi-arity 2 4 6)
(multi-arity 2 4)
(multi-arity 2)

(defn x-chop
  "Is it choping the wood or the pork chop?"
  ([name verb] (str name " " verb " pork chops."))
  ([name] (x-chop name "chops")))

(x-chop "John" "eats")
(x-chop "John")

;; rest parameters
(defn codger
  [name] (str "Get over here " name "!"))
(defn how-long
  [& names]
  (map codger names))
(how-long "paul" "john" 1 , 2)

(defn favorite-things
  [name & things] ;; first param is single, 2nd is a rest-parameters
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))
(favorite-things "Doreen" "gum" "shoes" "kara-te")

; varargs
(defn p-rest [& stuff]
  (println stuff))

(p-rest 1 2 3 4)

(println "Enter your name")
(def rl (read-line))
(str "Hello! " (clojure.string/capitalize rl))

(def equals #(=  %1 %2))
(equals "string" "string")

(defn hello [msg] (str "hello " msg "!"))
(hello "world")

(def result (let [x 5] (+ 1 x)))
(result)
