;; Based on the suggest from this video https://youtu.be/7SKVQyA4TjU?si=Ce_l80R7RnQvQiHP
;; We will learn by doing a project
;; A text-based adventure game

(comment
  "Build a project is the fastest way to learn a programming language,
  binding to this principle, we are doing a text-based adventure game;
  where the system (Clojure) will interact with 
  user through console inputs.
  
  First the system will introduce the user to the game,
  followed by a serioues of questions to setup the character,
  such as name, job, strength, defence, based on the job there will be a set
  of skills he/she can use to defeat the monster, but for sake of simplicity
  there will only 2 jobs, Warrior and Wizard.
  
  The character will have a HP and MP, that allows the user to attack
  if their MP is over the cost of the attack, 
  the MP will regenerate 1 point each round.

  The character is able to go different part of the forest 
  face off with different monsters
  ")

;; The system will take user inputs
(ns adventure.main
  (:require
   [clojure.data.json :as json]
   [clojure.string :as str])
  (:gen-class))
(def json (json/read-str (slurp "./resources/game.json") :key-fn keyword))

(defn ask [question]
  (print (str question "\n> " ))
  (flush)
  (read-line))

(defn valid-int-input 
  "validate the input is an int within a given range."
  [input min max] 
  (and (>= (Integer/parseInt input) min) (<= (Integer/parseInt input) max)))

(def worlds [:fantasy, :space, :earth])
(def jobs {1 :warrior 2 :druid 3 :librarian})
(def job-stats {
                :warrior { :title "Mighty Warrior" :hp 200, :attack 20}
                :druid { :title "Powerful Druid" :hp 250, :attack 15}
                :librarian { :title "Tremendous Librarian" :hp 150, :attack 30}
                })

(defn picked-world 
  "Player world selection in the beginning on the game."
  [worlds] 
  (let [reply (ask "What's your name?")] 
    (println (str "Nice to meet you! " reply)) 
    (let [choice (ask "Let the game begin, pick your world.
1: Fantasy World
2: Space
3. Current Earth.\n")] 
      (nth worlds (- (Integer/parseInt choice) 1)))))
(defn pick-job
  "Player select a job and return a job with stats"
  [jobs job-stats]
  (let [job-num (ask "Pick a job (1-3)!
1. Warrior
2. Druid
3. Librarian
")]
    (if (valid-int-input job-num 1 3)
      (let [job-key (get jobs (Integer/parseInt job-num))
            stats (get job-stats job-key)]
        {job-key stats})
      (println "Invalid input, please enter 1-3"))))

(defn get-rand-monster 
  "Randomly selected monster based on the world."
  [world-data] 
  (let [monsters (:monster world-data)]
    (nth monsters (+ 0 (rand-int (- (count monsters) 0 ))))))
(def w {:war 1})
(first(keys w ))
(defn game-start [world-data job]
  (println "\nYou are in:" (:name world-data) "as a" (:title job))
    (println "if you want to exit type quit")
    (loop []
      (let [input (ask "Pick a direction: north, east, south, west\n")]
        (if (= input "quit")
          (println "Adventure over! Bye!")
          (do
            (println "You are going to" (str/trim input))
            (println "Entered" ((keyword input) (:direction world-data)))
            (println "some monster spawned...")
             (let [monster (get-rand-monster world-data)]
               (println monster ))))
            (recur))))

(defn -main
  "The main entry point for the game" [& args]
  (println "Welcome!")
  (let [world-data (get-in json [:world (picked-world worlds)])
        job (pick-job jobs job-stats)]
            (game-start world-data job)))
