(ns tic-tac-toe)


(def game-state
  {:current-player "x"
   :board [nil "x" "o" nil nil "x" "o" nil nil]
   :history [{:player "x"
              :location 1}
             {:player "o"
              :location 2}
             {:player "x"
              :location 5}
             {:player "o"
              :location 6}]})

(defn moves-played [game-state]
  (count (game-state :history)))

(moves-played game-state)

(def tic-tac-toe
  {:current-player nil
   :board [nil nil nil nil nil nil nil nil nil]
   :history []})

(assoc tic-tac-toe :current-player "x")
(def updated-tic-tac-toe
  (assoc tic-tac-toe :board
         (assoc (:board tic-tac-toe) 1 "x")))
(def v [ nil nil nil ])
(assoc v 1 "x")
(assoc v 0 "0")