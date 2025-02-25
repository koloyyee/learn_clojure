(ns file
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn rev-read
  "Read in a text file (assuming it's in the current directory), 
  	reverse the file's contents, 
  	and write the result to a new file (the name now prefixed by 'rev-')."
  [from to]
  (let [file (io/file from)]
    (if (.exists file)
      (->> file
           slurp
           str/reverse
           (spit to))
      (println "No such file."))))

(rev-read "src/hello.txt" "src/olleh.txt")