(ns the-divine-cheese-code.core
  (:require
   [clojure.java.browse :as browse]
   [the-divine-cheese-code.visualization.svg :refer [xml]])
  (:gen-class))

;; (ns the-divine-cheese-code.core
  ;; (:gen-class)
	;; few ways to import.
	;; 1. as alias

;; 2. import all
  ;; (:require
  ;;  [the-divine-cheese-code.visualization.svg :refer ])

;; exclude function
  ;; (:refer-clojure :exclude [println])
 	;; )

;; Ensure that the SVG code is evaluated
;; Refer the namespace so that you don't have to use the 
;; fully qualified name to reference svg functions
   
;; they are all basically the same.

;; (use) is not good for production.
(use 'the-divine-cheese-code.visualization.svg)
;; require and refer are more preferred
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)
(use '[the-divine-cheese-code.visualization.svg :as svg])

;; https://www.braveclojure.com/organization/#The_ns_Macro
;; There are six possible kinds of references within ns:

;; (:refer-clojure)
;; (:require)
;; (:use)
;; (:import)
;; (:load)
;; (:gen-class)


(def heists [{:location "Cologne, Germany"
              :cheese-name "Archbishop Hildebold's Cheese Pretzel"
              :lat 50.95
              :lng 6.97}
             {:location "Zurich, Switzerland"
              :cheese-name "The Standard Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Marseille, France"
              :cheese-name "Le Fromage de Cosquer"
              :lat 43.30
              :lng 5.37}
             {:location "Zurich, Switzerland"
              :cheese-name "The Lesser Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Vatican City"
              :cheese-name "The Cheese of Turin"
              :lat 41.90
              :lng 12.45}])

(defn url
  [filename]
  (str "file:///"
       (System/getProperty "user.dir")
       "/"
       filename))

(defn template
  [contents]
  (str "<style>polyline { fill:none; stroke:#5881d8; stroke-width:3}</style>"
       contents))

(defn -main
  [& args]
  (let [filename "map.html"]
    (->> heists
         (xml 50 100)
         template
         (spit filename))
    (browse/browse-url (url filename))))