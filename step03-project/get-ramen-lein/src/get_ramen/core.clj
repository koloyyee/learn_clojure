(ns get-ramen.core
  (:require
   [clj-http.client :as client])
  (:gen-class))

(defn fetch-data [url]
  (try
    (let [response (client/get url {:accept :json :as :json})]
      (if (= 200 (:status response))
        (:body response)
        (println "Error: " (:status response))))
    (catch Exception e
      (println "Error fetching data:" (.getMessage e)))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [ data (fetch-data "https://ramen-api.dev/shops")]
    (println "Fetched data:" data )))
