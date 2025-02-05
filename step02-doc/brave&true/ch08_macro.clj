(ns macro)

(defmacro infix
  "Use this macro when you pine for the notation of your childhood"
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(macroexpand '(infix (1 + 1)))

;; Destructuring arguments 
(defmacro infix-2
  [[operand1 op operand2]]
  (list op operand1 operand2))

(macroexpand '(infix-2 (1 + 2)))


;; why 'let and 'result????
;; The reason this happens is that your macro body tries 
;; to get the value that the symbol let refers to, 
;; whereas what you actually want to do is return 
;; the let symbol itself.

;; you’re trying to get the value of result, which is unbound, 
;; and you’re trying to get the value of println instead of 
;; returning its symbol
(defmacro my-print-whoopsie
  [expression]
  (list 'let ['result expression]
        (list println 'result)
        'result))
(my-print-whoopsie (+ 1 2))

;;  ' (quote) turn it into a symbol which will not be execute right away.


;; checkout the when macro
(when (= 1 1) "hello")
(macroexpand '(when (= 1 1) "hello"))

`(+ 1 ~(inc 1))
`(when (= 1 1) "hello")

;; few kinds of Syntax Quoting
;; 1. ' - Quoting does not include a namespace if your code doesn’t include a namespace
;; 2. ` - Syntax quoting will always include the symbol’s full namespace
;; 3. ~ - allows you to unquote forms using the tilde
;; 4. @ - symbol to dereference a reference type

'(+ 1 (inc 1))
`(+ 1 (inc 1))
'(+ 1 ~(inc 1))
`(+ 1 ~(inc 1))

'(when (= 1 1) "hello")
`(when (= 1 1) "hello")
'(when ~(= 1 1) "hello")
`(when ~(= 1 1) "hello")


(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))

(code-critic (1 + 1) (+ 1 1))
(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))

(code-critic (1 + 1) (+ 1 1))
(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(println "Great squid of Madrid, this is bad code:"
            (quote ~bad)
            (println "Sweet gorilla of Manila, this is good code:"
                     (quote ~good))))
(code-critic (1 + 1) (+ 1 1))

(do (println "Great squid of Madrid, this is bad code:"
             (quote ~(1 + 1))))

(defn criticize-code [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code: " bad)
       ~(criticize-code "Sweet sacred boa of Western and Eastern Samoa, this is good code: " good)))

(code-critic (1 + 1) (+ 1 1))


;; NPE example in macro
;; The problem is that map returns a list, and in this case, 
;; it returned a list of println expressions
(defmacro code-critic
  [bad good]
  `(do ~(map #(apply criticize-code %)
             [["Great squid of Madrid, this is bad code:" bad]
              ["Sweet gorilla of Manila, this is good code:" good]])))
(code-critic (1 + 1) (+ 1 1))

;; Explanation 
;; why?
;; works like this, run it
(macroexpand '(code-critic (1 + 1) (+ 1 1)))
;; first became this
(do
  ((clojure.core/println "criticism" '(1 + 1))
   (clojure.core/println "criticism" '(+ 1 1))))
;; then
(do
  (nil
   (clojure.core/println "criticism" '(+ 1 1))))
;; finally
(do
  (nil nil))
;; see println? it will be nil, so defmacro will run the (do nil nil )
(eval (do (nil nil)))

;; 
;; ~@ : Solution: Unqouting Splicing the list.
;; from:
`(+ ~(list 1 2 3))
;; to:
`(+ ~@(list 1 2 3))

(defmacro code-critic
  [bad good]
  `(do ~@(map #(apply criticize-code %)
              [["Sweet lion of Zion, this is bad code:" bad]
               ["Great cow of Moscow, this is good code:" good]])))
(code-critic (1 + 1) (+ 1 1))
(macroexpand '(code-critic (1 + 1) (+ 1 1)))

;; Variable Capture
;; 
;; Variable capture occurs when a macro introduces a binding that, 
;; unknown to the macro’s user, eclipses an existing binding. 
;; 
;; For example, in the following code, a macro 
;; mischievously introduces its own let 
;; binding, and that messes with 
;; the code:
(def message "Good job!")
(defmacro with-mischief
  [& stuff-to-do]
 	;; here the argument "message" won't be evaluated
  (concat (list 'let ['message "Oh, big deal!"])
          stuff-to-do))
(with-mischief
  (println "Here's how I feel about that thing you did: " message))
(with-mischief (print "something" "here"))
;; what if we don't quote the binding variable and unqoute splicing
(defmacro with-mischief
  [& stuff-to-do]
  '(let [message "Oh, big deal!"]
     ~@stuff-to-do))
(with-mischief
  (println "Here's how I feel about that thing you did: "))

;; Solution: gensym, generate unique symbol
(gensym 'message)
(gensym 'message)

;; if we change from ` to ' there will be difference
;; notice Calva will show it won't recognize it.
;; if we use ' here are error messages:
; Syntax error macroexpanding clojure.core/let at (ch08_macro.clj:183:1).
; (clojure.core/unquote macro-message) - failed: simple-symbol? at: [:bindings :form :local-symbol] spec: :clojure.core.specs.alpha/local-name
; (clojure.core/unquote macro-message) - failed: vector? at: [:bindings :form :seq-destructure] spec: :clojure.core.specs.alpha/seq-binding-form
; (clojure.core/unquote macro-message) - failed: map? at: [:bindings :form :map-destructure] spec: :clojure.core.specs.alpha/map-bindings
; (clojure.core/unquote macro-message) - failed: map? at: [:bindings :form :map-destructure] spec: :clojure.core.specs.alpha/map-special-binding
(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))
(without-mischief
 (println "Here's how I feel about that thing you did: " message))

;; Explanation
;; 
;; This example avoids variable capture by using gensym 
;; to create a new, unique symbol that then gets bound 
;; to macro-message
;; 
;; Within the syntax-quoted let expression, macro-message 
;; is unquoted, resolving to the gensym’d symbol. 
;; This gensym’d symbol is distinct from 
;; any symbols within stuff-to-do, 
;; so you avoid variable capture


;; auto-gensym
;; you create an auto-gensym by appending a hash mark
;; to a symbol within a syntax-quoted list.
;; 
;; Clojure automatically ensures that each instance of x# 
;; resolves to the same symbol within the same syntax-quoted list, 
;; that each instance of y# resolves similarly
`(blarg# blarg#)
(blarg__2869__auto__ blarg__2869__auto__)
`(let [name# "Larry Potter"] name#)

(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message 'message#] ;; this will work with auto-gensym
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))
(without-mischief
 (println "Here's how I feel about that thing you did: " message))

;;  gensym and auto-gensym are both used all the time 
;; when writing macros, and they allow you to avoid 
;; variable capture.

;; Double Evaluation
;; Problem in this code
(defmacro report
  [to-try]
  `(if ~to-try
     (println (quote ~to-try) "was successful:" ~to-try)
     (println (quote ~to-try) "was not successful:" ~to-try)))
;; the problem in this code where it slept twice
(report (do (Thread/sleep 1000) (+ 1 1)))
;; Solution: gensym
(defmacro report
  [to-try]
  `(let [result# ~to-try]
     (if result#
       (println (quote ~to-try) "was successful: " ~to-try)
       (println (quote ~to-try) "was unsuccessful: " ~to-try))))
(report (do (Thread/sleep 1000) (+ 1 1)))
(report (= 1 1))
(report (= 1 2))

;; use doseq
(doseq [code ['(= 1 1) '(= 1 2)]]
  (report code))

(defmacro doseq-macro
  [macroname & args]
  `(do
     ~@(map (fn [arg] (list macroname arg)) args)))

(doseq-macro report (= 1 1) (= 1 2))

;; About using macro
;; If you are ever in this situation, 
;; take some time to rethink your approach. 
;; 
;; It’s easy to paint yourself into a corner, 
;; making it impossible to accomplish anything 
;; with run-of-the-mill function calls. 
;; You’ll be stuck having to write more macros instead. 
;; 
;; Macros are extremely powerful and awesome, 
;; and you shouldn’t be afraid to use them. 
;; They turn Clojure’s facilities for working with data 
;; into facilities for creating new languages informed 
;; by your programming problems. 
;; 
;; For some programs, it’s appropriate for your code 
;; to be like 90 percent macros. 
;; As awesome as they are, 
;; they also add new composition challenges. 
;; They only really compose with each other, 
;; so by using them, you might be missing out 
;; on the other kinds of composition (functional, object-oriented) 
;; available to you in Clojure.



;; Example: Brews for the Brave and True

;; missing @
(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmonsgmail.com"})


(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

;; validation function
(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))
;; The first argument, to-validate, is the field you want to validate. 
;; The first element of the pair should be an error message
;; 
;; The second argument, message-validator-pairs, 
;; should be a seq with an even number of elements. 
;; This seq gets grouped into pairs with (partition 2 message-validator-pairs)
;; the second element of the pair should be a function

(error-messages-for "" ["Please enter a name" not-empty])

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))
(validate order-details order-details-validations)


;; won't work
;; this wouldn’t work, because success-code and failure-code
(defn if-valid
  [record validations success-code failure-code]
  (let [errors (validate record validations)]
    (if (empty? errors)
      success-code
      failure-code)))
;; will work
;; A macro would work because macros let you control evaluation.
(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

(def my-error-name {:name ""})

(if-valid order-details order-details-validations my-error-name
          (println :success)
          (println :failure my-error-name))
(macroexpand
 '(if-valid order-details order-details-validations my-error-name
            (println :success)
            (println :failure my-error-name)))
(let*
 [my-error-name (validate order-details order-details-validations)]
 (if (clojure.core/empty? my-error-name)
   (println :success)
   (println :failure my-error-name)))
	

;; Exercise:
;; 1.
(defmacro when-valid
  [to-validate validations  & else-action] 
 	`(let [error# (validate ~to-validate ~validations)]
    	(if (empty? error#) 
        (do  ~@else-action))))

(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmons@gmail.com"})
(def render {:success "welcome!"})
(when-valid order-details order-details-validations
            (println "It's a success!")
            (render :success))
