(ns ch03-do-things
  (:require
   [clojure.string]))

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
((fn [x] (* 2 x)) 10)

;; default or fallback value. this is very useful just like nullish
(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")

;; Vectors
[3 2 1]
(get [3 2 1] 1)
(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)

(get (get ["a" {:name "Pugsley Winterbottom"} "c"] 1) :name)
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
(conj #{:a :b} :c)
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

(defn weird-arity
  ([]
   "Destiny dressed you this morning, my friend, and now Fear is
     trying to pull off your pants. If you give up, if you give in,
     you're gonna end up naked with Fear just standing there laughing
     at your dangling unmentionables! - the Tick")
  ([number]
   (inc number)))
(weird-arity)
(weird-arity 2)

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

;; deconstruction
(defn my-first
  [[first-thing]]
  first-thing)
(my-first [2 3 4])

(defn chooser [[first-thing second-thing & rest-things]]
  (println (str "First choice is " first-thing))
  (println (str "Second choice is " second-thing))
  (println (str "Rest choices are "
                "Here they are: "
                (clojure.string/join ", " rest-things))))
(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])

(defn case-when [[first-choice second-choice & rest-choices]]
  (when (= first-choice 1) (println (str "Team 1!"))
        (when (= second-choice 2) (println (str "Team 2!"))
              (when (println (str "Combine to " (reduce + rest-choices)))))))
(case-when [1 2 3 4])

;; deconstruct map

(def me-map {:name "David", :age 38})

(defn destruct-map
  "inside param, we use {} to destruct map, n and a are any word that represents the keyword like :name :age"
  [{n :name a :age}]
  (println (str n  " " a)))
(destruct-map me-map)

;; get Keywords
(defn keys-only
  "get the keys only with :keys, the params name must be the same as keywords"
  [{:keys [name age]}]
  (println (str "My name is " name))
  (println (str "I am " age " years old")))

(keys-only me-map)

;; access original 
(defn access-original
  "retain access to the original map argument by using the :as keyword."
  [{:keys [name age] :as me-map}]
  (println (str "My name is " name))
  (println (str "I am " age " years old")))

(access-original me-map)

;; Function body
(defn who-showing
  "Clojure automatically returns the last form evaluated"
  []
  (* 1 3 3 4)
  30
  "hello world")
(who-showing)

(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! What a big number!"
    "That number's OK, I guess"))
(number-comment 5)
(number-comment 7)

#_(str " Clojure has no privileged functions. "
       "+ is just a function, - is just a function, "
       "and inc and map are just functions. "
       "They’re no better than the functions you define yourself. So don’t let them give you any lip!")

; Anonymous Functions
(map (fn [name] (str "Hi, " name)) ["Darth Vader", "Yoda"])
((fn [x] (* x 3)) 8)
(map #(+ % 1) [1 2 3 4 5])
(println (#(str %1 " and " %2) "cornbread" "tortilla"))
(#(identity %&) 2 "bling blang" :name)
(#(identity %&) 1 "bling" (get {:name "Taylor"} :name))

; Returning Functions
(defn inc-maker
  "The returned functions are closures, which means that they can access 
   all the variables that were in scope when the function was created."
  [inc-by]
  #(+ % inc-by))
(def inc3 (inc-maker 3))
(inc3 7)

(defn inc4 [x] (#(+ % 4) x))
(inc4 4)

;; Pull them all together 
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})
(defn symmetrize-body-parts
  "Expects a seq of maps that hae a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))
(symmetrize-body-parts asym-hobbit-body-parts)

;; what is let?
;; let binds names to values, but only within the scope.
(let [x 3] x)
;; x ;;<- uncomment and see.
(def dalmetian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [dal (take 2 dalmetian-list)]
  dal)
(def x 0) ;; def var lives in this ns
x
(let [x 1] x) ;; let var lives inside a scope
x
(let [x (inc x)] x)
x
(let [[pongo & dalmatians] dalmetian-list]
  [pongo dalmatians])

;; loop, clj's recusion
(loop [iteration 0]
  "this is a loop and recur version"
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

(defn loopy [x]
  (loop [iteration 0]
    (println (str "Iteration " iteration))
    (if (> iteration x)
      (println "Goodbye!")
      (recur (inc iteration)))))

(loopy 5)

;; plain recursion
(defn recursive-printer
  "this is a plain recursion"
  ([]
   (recursive-printer 0))
  ([iteration]
   (println (str "Iteration " iteration))
   (if (> iteration 3)
     (println "Goodbye")
     (recursive-printer (inc iteration)))))

(recursive-printer)

;; RegEx
(re-find #"^left-" "left-hand")
(re-find #"[0-9]+[a-d]+" "10abbaeeek")
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})
(matching-part {:name "left-eye" :size 1})
(matching-part {:name "head" :size 3})

;; Higher order function 
(reduce + 15 [1 2 3 4])
(map #(* % 2) [1 2 3 4])
(defn my-reduce
  "own implementation of reduce"
  ([f init coll] ; take 3 arguments
   (loop [result init remain coll] ; set temp var
     (if (empty? remain) result
         (recur (f result (first remain)) (rest remain)))))
  ([f [head & tail]]
   (my-reduce f head tail)))
(my-reduce + 0 [1 2 3])

;; improved version
(defn better-symmetric-body-parts
  "improved version"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
          ; from a set of parts into final-body-parts
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))
(better-symmetric-body-parts asym-hobbit-body-parts)
;; same result but different style
(symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetric-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts ;; finding the part
           accumulated-size (:size part)]
      (if (> accumulated-size target) part
          (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)
;; IO 
(println "Enter your name")
(def rl (read-line))

(str "Hello! " (clojure.string/capitalize rl))

(def equals #(=  %1 %2))
(equals "string" "string")

(defn hello [msg] (str "hello " msg "!"))
(hello "world")

(def result (let [x 5] (+ 1 x)))
(result)

;; Exercise
;; 1. Use the str, vector, list, hash-map, and hash-set functions.
(str "usej" " the str function" "!")
(into [1 2 3 4] [5 6 7 8])
(into '(5 6 7 8) (conj  (list 1 2 3 4) 5))

(get-in (into {:a 1, :b 2}
              {:c 3, :d 4 , :b 6, :q {:d 2, :e 5}})
        [:q :d])
(conj #{1 2 3 4 5 6} 6)
; 2.
(defn add-100 [x] (+ 100 x))
(add-100 10)
(defn add-hundred [x]
  ((fn [n] (+ 100 n))
   x))
(add-hundred 10)
(defn add-mill [x] (#(+ % 100) x))
(add-mill 10)
; 3.
(defn dec-maker [x] (fn [n] (- n x)))
(defn dec9 [x] ((dec-maker 9) x))
(dec9 10)
; 4.
(defn mapset [f coll]
  (let [set-coll (into #{} coll) ]
  (into #{} (map f set-coll))
  ))
(mapset inc [ 1 1 2 2 ])