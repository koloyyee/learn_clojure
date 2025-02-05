(ns webapp.db
  (:require
   [next.jdbc.sql :as sql]))

(def db-spec {:dbtype "h2" :dbname "./my-db"})

(defn add-location-to-db
  [x y]
 	(let [results (sql/insert! db-spec :locations {:x x :y y})]
   	(assert (and (map? results ) (:LOCATIONS/ID results )))
  		results))

(defn get-xy [loc-id]
  (let [results (sql/query db-spec ["SELECT * FROM locations WHERE id = ? " loc-id])]
   	(assert (= ( count results ) 1 ))
  		(first results )))

(defn get-all-locations
  []
 	(sql/query db-spec ["SELECT id, x, y FROM locations "]))

;; this is for debugging!!! so cool!
(comment 
  (get-all-locations)
 	(get-xy 1))