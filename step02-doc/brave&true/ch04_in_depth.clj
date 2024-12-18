;; Building Abstraction

;; map always return a sequence
(defn titleize [topic] 
  (str topic " for the Brave and True"))
(map titleize ["Hamster", "Tuna"])
(map titleize '("Manchester", "Body shop"))
(map titleize #{"Shark", "Mash Potato"})
(map  #( titleize (second % )) {:uncomfortable "Winking"})

;; first, rest and cons
;; seq always returns a value that looks and behaves like a list;
(seq '(1 2 3 ))
(seq [ 1 2 3 ])
(seq #{ 1 2 3 })
(def name-map (seq {:name "Bill Compton" :occupation "Dead mopey guy" :age 29}))
(first name-map)
(rest name-map)
;; convert from seq to list, vector, etc. with "into"
(into '() (seq '(1 2 3 ))) ;; notice the order
(into [] (seq '(1 2 3 ))) ;; notice the order
;; cons is conj for seq
(cons 0 (seq [1 2 3 ])) 
(cons 0 (seq '(1 2 3 ))) ;; cons is conj for seq

;; map
(map inc [1 2 3]) ;; apply inc to each element
(map str ["a" "b" "c"] ["A" "B" "C"]) 
; the same as 
(list (str "a" "A") (str "b" "B") (str "c" "C"))

; all together
(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
;; left is element from human-consumption, right from critter-consumption 
  [human critter] 
 	;; return a hash-map
  {:human human
   :critter critter})
(map unify-diet-data human-consumption critter-consumption)

;; create our own higher-order functions
(def sum #(reduce + % )) ;; sum is using reduce by add each element together 
(def avg #(/ (sum %) (count %))) 
(defn stats [numbers]
  (map #(% numbers) [sum count avg])) ;; putting numbers in each function in the vector.
(stats [3 4 10])
(stats [80 1 44 13 6])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
(map :real identities)

;; reduce
(reduce
 (fn [new-map [key value]];; takes a map, deconstruct to key and value 
 (assoc new-map key (* value 2 )))  ;; new-map is {} from next line
 {} ;; this is new-map
 {:max 30 :min 10})
 ;; assoc handles hash-map

(reduce
 (fn [new-map [key value]];; takes a map, deconstruct to key and value 
   (assoc new-map key (* value 2 )))  ;; new-map is {} from next line
	 {}
 (assoc (assoc {} :max (inc 30)) :min (inc 10)))
 (assoc (assoc {} :max (inc 30)) :min (inc 10))

(reduce (fn [new-map [k v ]]
          (if (> v 4 )
            (assoc new-map k v )
            new-map))
        {:see-me 4.0}
        {:human 4.1 :critter 3.9})

;; take, drop 
(def sample-list [1 2 3 4 5 6 7 8 9 0])
;; take n elements from a collection
((fn [n] (take n [ 1 2 3 4 5 6 7 8 9 0])) 
 3) 
((fn [n] (take n '(1 2 3 4 5 6 7 8 9 0))) 3) 
((fn [n] (take n #{1 2 3 4 5 6 7 8 9 0})) 3) 
;; drop the firs n elements, opposite of take
(drop 3 sample-list) 


(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])
;; take-while, and drop-while
;; take all the map with :month less than 3
(take-while #(< (:month %) 3 ) food-journal) 
;; drop all the map with :month less than 3
(drop-while #(< (:month %) 3 ) food-journal)

;; get :month 2 - 3 
(take-while #(< (:month %) 4) ;; take 1 - 3 
(drop-while #(< (:month % ) 2 )  ;; drop 1 -> retain  2 - 3 
            food-journal))

;; filter and some
;; filter, can be slower than take-while
(filter #(< % 5) sample-list)
(filter #(< (:human %) 5) food-journal)
(filter #(= (:critter %) 3.3) food-journal)
;; some, just like javascript .some
(some #(> (:critter %) 5) food-journal)
(some #(> (:critter %) 3) food-journal)
;; Here, a slightly different anonymous function uses 
;; and to first check whether the condition (> (:critter %) 3) is true, 
;; and then returns the entry when the condition is indeed true.
(some #(and (> (:critter %) 3) %) food-journal)

;; sort and sort-by
(sort [ 2 3 4 ])
(into [] 
      (sort-by count ["aaa" "b" "c"]);; returns a seq 
			)
(concat [1 2 ] [3 4 ])

;; Lazy Seq
;; A lazy seq is a seq whose members aren’t computed until you try to access them
;; Computing a seq’s members is called realizing the seq.
;; Deferring the computation until the moment it’s needed makes your programs more efficient

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  "get vampire by social security number"
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  "is this a vampire?"
 	[record]
 	(and (:makes-blood-puns? record) ;; makes-blood-puns is true
      	(not (:has-pulse? record)) ;; has-pulse is false
   				record))
(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))
;; map is lazy therefore it will be more efficient
(time (def mapped-details (map vampire-related-details (range 0 1000000))))
(time (first mapped-details))
;; this is less efficient because of filter needs iterate through the data
(time (identify-vampire (range 0 1000000)))

;; a lazy seq as consisting of two parts: 
;; a recipe for how to realize the elements of a sequence 
;; and the elements that have been realized so far. 

;; create an infinite sequence is with repeat
(concat 
 (take 8 ;; take the first 8 "na"
       (repeat "na")) ;; seq with infinite "na"
 ["batman"]) ;; add batman to the "na"s

(take 3 (repeatedly ;; repeatedly, which will call the provided function to generate each element in the sequence
         #( rand-int 10)))

(defn even-numbers 
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2 ))))))
(take 10 (even-numbers))

(map identity {:sunlight-reaction "Glitter!"})
(into {} (map identity {:sunlight-reaction "Glitter!"}))
;; into will turn the "from" into the "to" data structure.
(into ["cherry"] #{"apple" "pear"})
;; map
(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
;; difference between conj and into
;; conj takes a rest parameter 
;; into takes a seqable data structure
(conj [0] [1 2]) 
(into [0] [1 2]) 
(conj [0] 1 2 3 4) 
#_(into [0] 1 2 3 4) ;; this is will failed
(conj {:time "midnight"} [:place "ye olde cemetarium"])
(defn my-conj
  [target & additions]
;; into takes a seqable data structure
  (into target additions))
(my-conj [0] 1 2 3 )

;; Function Functions
(max 0 1 2 )
(max [0 1 2]) ;; this just 1 parameter
(apply max [0 1 2 ]) ;; apply max to 0 1 2 
(defn my-into 
  [target  additions]
;; conj takes a rest parameter 
  (apply conj target additions))
(my-into [0] [1 2 3])

;; Partials
;; partial takes a function and rest parameters
(def add10 (partial + 10))
(add10 10)
(def add-missing-elements
  (partial conj ["water" "earth" "air"]))
(add-missing-elements "unobtainium" "adamantium")

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))
(def add20 (my-partial +  1 1 1 ))
(add20 1 1 )

(defn lousy-logger
  [log-level message]
  (condp = log-level
  :warn (clojure.string/lower-case message)
  :error (clojure.string/upper-case message)))
(def warn (partial lousy-logger :warn))
(warn "Red light ahead")

(defn identify-human
[social-security-number]
(filter #(not (vampire? %))
(map vampire-related-details social-security-number)))

;; complement as in the negative, like in binary
(def not-vampire? (complement vampire?))
(defn identify-human
  [social-security-numbers]
  (filter not-vampire? 
  (map vampire-related-details social-security-numbers)))

(defn my-complement
  [func]
  (fn [& args]
  (not (apply func args)))) ;; apply is needed because args is seq
(def my-pos? (my-complement neg?))
(my-pos? 1)
(my-pos? -1)
