
;; Futures
;; use futures to define a task and place it on another thread without requiring the result immediately
(future (Thread/sleep 4000)
        (println "I'll print after 4 seconds"))
(println "I'll print immediately")

;; future function returns a reference value that you can use to request the result
(let [result (future (println "this prints once")
                     (+ 1 1))]
  (println "deref: " (deref result))
  (println "@: " @result)) ;; 

;; Requesting a future’s result is called dereferencing the future, 
;; and you do it with either the deref function or the @ reader macro.
(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "The result is: " @result)
  (println "It will be at least 3 seconds before I print"))

;; limit on how long to wait for a future. To do that, you can pass deref a number of milliseconds to wait 
;; along with the value to return if the deref times out:
(deref (future (Thread/sleep 1000) 0) 10 5)

;;  interrogate a future using realized? to see if it’s done running
(realized? (future (Thread/sleep 1000)))

(let [f (future)]
  @f
  (realized? f))
;; Delay
;; @ is a reader macro that dereferences a delay, promise, or future
;; to get the result.
(def jackson-5-delay
  (delay (let [message "Just call my name and I'll be there"]
           (println "First deref:" message)
           message)))
;; force behaves identically to deref in that it communicates more clearly that you’re causing a task to start as opposed to waiting for a task to finish
@jackson-5-delay ;; before deref
(force jackson-5-delay)
@jackson-5-delay

(def gimli-headshots ["serious.jpg" "fun.jpg" "playful.jpg"])
(defn email-user
  [email-address]
  (println "Sending headshot notification to" email-address))
(defn upload-document
  "Needs to be implemented"
  [headshot]
  true)
(let [notify (delay  (email-user "and-my-axe@gmail.com"))]
  (doseq [headshot gimli-headshots]
    (future (upload-document headshot)
              (force notify))))
;; Promise
;; You create promises using promise and deliver a result 
;; to them using deliver. You obtain the result by dereferencing
(def my-promise (promise))
(deliver my-promise (+ 1 2))
@my-promise




(def yak-butter-international
  {:store "Yak Butter International"
   :price 90
   :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))
;; no future
(time (some (comp satisfactory? mock-api-call)
            [yak-butter-international butter-than-nothing baby-got-yak]))
;; future
(time
 (let [butter-promise (promise)]
   (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
     (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
               (deliver butter-promise satisfactory-butter))))
   (println "And the winner is:" @butter-promise)))
(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(let [saying3 (promise)]
  (future (deliver saying3 (wait 100 "Cheerio!")))
  @(let [saying2 (promise)]
     (future (deliver saying2 (wait 400 "Pip pip!")))
          @(let [saying1 (promise)]
               (future (deliver saying1 (wait 200 "'Ello, gov'na!")))
               (println @saying1)
               saying1)
     (println @saying2)
     saying2)
  (println @saying3)
  saying3)

(defmacro enqueue
    ([q concurrent-promise-name concurrent serialized]
           `(let [~concurrent-promise-name (promise)]
               (future (deliver ~concurrent-promise-name ~concurrent))
                      (deref ~q)
               ~serialized
               ~concurrent-promise-name))
     ([concurrent-promise-name concurrent serialized]
       `(enqueue (future) ~concurrent-promise-name ~concurrent ~serialized)))

(time @(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
           (enqueue saying (wait 400 "Pip pip!") (println @saying))
           (enqueue saying (wait 100 "Cheerio!") (println @saying))))