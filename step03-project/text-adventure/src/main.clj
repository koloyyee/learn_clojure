;; Based on the suggest from this video https://youtu.be/7SKVQyA4TjU?si=Ce_l80R7RnQvQiHP
;; We will learn by doing a project
;; A text-based adventure game

;; The system will take user inputs
(ns main
  (:require
   [clojure.data.json :as json]))
(println "Welcome!")

(defn ask [question] 
(println question) (read-line))

(def worlds [:fantasy, :space, :earth])
(defn picked-word [] (let [reply (ask "What's your name?")]
  (println (str "Nice to meet you! " reply))
  (let [choice (ask 
                "Let the game begin, pick your world.
1: Fantasy World
2: Space
3. Current Earth.")]
      (nth worlds (- (Integer/parseInt choice) 1 )))))

( picked-word)

(def json (json/read-str (slurp "./resources/game.json") :key-fn keyword))
(json/read-str (slurp "./resources/game.json") :key-fn keyword)
(get-in json [:world (picked-word)])
