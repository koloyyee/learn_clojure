(ns read-file
  (:require
   [clojure.java.io :as io]))

(defn read-f [line]
  (conj (not-empty line)))

(defn f
  "read inputs file by filename with file extention, e.g.: day01.txt"
  [filename] (with-open [rdr (io/reader (str "inputs/" filename))] ; line by line
               (mapv read-f (line-seq rdr))))

(defn read-lines
  "read inputs file by filename with file extension, e.g.: day01.txt"
  [filename]
  (with-open [rdr (io/reader (str "inputs/" filename))]
    (vec (line-seq rdr))))

