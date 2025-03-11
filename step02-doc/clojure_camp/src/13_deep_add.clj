(ns deep-add)

(defn add-info [info id key value]
  "Write a function that takes a map with this structure:

UNPARSED
as well as an id, a keyword, and some value.

Return the same map but with the person with the given id having the given keyword 
  & value added to their data (see tests for an example)."
  (assoc-in info [:people id key] value))
(add-info {:people {1 {:name "james" :points 1} 2 {:name "rafd" :points 5}}}
          1
          :stuff
          "beep boop")

(= (add-info {:people {1 {:name "james" :points 1}
                       2 {:name "rafd" :points 5}}}
             1
             :stuff
             "beep boop")
   {:people {1 {:name "james"
                :points 1
                :stuff "beep boop"}
             2 {:name "rafd"
                :points 5}}})