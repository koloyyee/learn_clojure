(ns ch06_org)

;; The primary tool in Clojure for storing objects is def
(def great-books ["East of Eden" "The Glass Bead Game"])

;; You can interact with a namespace’s map of symbols-to-interned-vars using ns-interns
(ns-interns *ns*)
(get ( ns-interns *ns*) 'great-books)

;; #'user/great-books is the reader form of a var
;; deref vars to get the objects they point to
(deref #'user/great-books)


(def great-books ["The Power of Bees" "Journey to Upstairs"])


;; Creating and Switching to Namespaces

;; create-ns takes a symbol, creates a namespace with that name if it doesn’t exist already,
(create-ns 'cheese.taxonomy)

;; returned namespace as an argument in a function call
(ns-name (create-ns 'cheese.taxonomy))

;; in practice we will not use (create-ns) but to use (in-ns)
;; (in-ns) will switch to the namespace.
(in-ns 'cheese.analysis)


;; test in the REPL
;; switch back to 'cheese.taxonomy
(in-ns 'cheese.taxonomy)
(def cheddar ["mild" "medium" "strong" "sharp" "extra sharp"])
(in-ns 'cheese.analysis)
;; cheddar 


;; refer gives you fine-grained control over how you refer to objects in other namespaces.
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])
(in-ns 'cheese.analysis)
(clojure.core/refer 'cheese.taxonomy)
(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'bries)
;; It’s as if Clojure
;; Calls ns-interns on the cheese.taxonomy namespace
;; Merges that with the ns-map of the current namespace
;; Makes the result the new ns-map of the current namespace


;; use :only and :exclude with clojure.core/refer
(clojure.core/refer 'cheese.taxonomy :only ['bries])

;;  define private functions using defn-
(in-ns 'cheese.analysis)

(ns here)
;; Notice the dash after "defn"
(defn- private-function
  "Just an example function that does nothing"
  [] 
	(println "private"))

(defn public-function [] (println "I am public"))